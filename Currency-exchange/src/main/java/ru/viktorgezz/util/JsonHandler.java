package ru.viktorgezz.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class JsonHandler {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void send(Object object, HttpServletResponse resp, int status) throws IOException {
        String json = objectMapper.writeValueAsString(object);
        resp.setStatus(status);
        resp.getWriter().write(json);
    }

    public void sendException(Object object, HttpServletResponse resp, int status) throws IOException {
        HashMap<String, String> messageError = new HashMap<>();
        messageError.put("error", object.toString());

        String json = objectMapper.writeValueAsString(messageError);
        resp.setStatus(status);
        resp.getWriter().write(json);
    }

    public <T> T get(BufferedReader reader, Class<T> clazz) throws IOException {
        if (reader == null || !reader.ready()) {
            throw new IOException("Invalid input data");
        }
        return objectMapper.readValue(reader, clazz); // добавить чтенние данного исключения
    }
}
