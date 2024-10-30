package ru.viktorgezz.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import ru.viktorgezz.util.exception.ParamException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AcceptParam {
    public static String getFirstParam(HttpServletRequest req, String titleParam) throws ParamException {
        try {
            InputStreamReader reader = new InputStreamReader(req.getInputStream());
            String[] par = new BufferedReader(reader).readLine().split("=");
            if (!par[0].equals(titleParam) || par.length != 2) {
                throw new ParamException("Неверный параметр");
            }
            return par[1];
        } catch (IOException e) {
            throw new ParamException("Ошибка обработки запроса");
        }
    }

    public static <T> T getClassFromParams(HttpServletRequest req, Class<T> clazz) throws ParamException {
        try {
            InputStreamReader reader = new InputStreamReader(req.getInputStream());
            String[] parKeyValue = new BufferedReader(reader).readLine().split("&");
            Map<String, String> json =
                    Stream.of(parKeyValue)
                    .map(s -> s.split("="))
                    .collect(HashMap::new, (m, a) -> m.put(a[0], a[1]), Map::putAll);

            return new ObjectMapper().convertValue(json, clazz);
        } catch (IOException e) {
            throw new ParamException("Ошибка обработки запроса");
        }
    }
}
