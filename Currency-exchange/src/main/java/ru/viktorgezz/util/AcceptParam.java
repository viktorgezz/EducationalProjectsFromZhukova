package ru.viktorgezz.util;

import jakarta.servlet.http.HttpServletRequest;
import ru.viktorgezz.util.exception.ParamException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AcceptParam {
    public static String getFirstParam(HttpServletRequest req) throws ParamException {
        try {
            InputStreamReader reader = new InputStreamReader(
                    req.getInputStream());
            BufferedReader br = new BufferedReader(reader);
            String data = br.readLine();

            String[] par = data.split("=");

            return par[1];
        } catch (IOException e) {
            throw new ParamException("Ошибка обработки запроса");
        }

    }
}
