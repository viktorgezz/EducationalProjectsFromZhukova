package ru.viktorgezz.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class JsonHandler {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void send(Object object, HttpServletResponse resp) throws IOException {
        String json = objectMapper.writeValueAsString(object);
        resp.getWriter().write(json);
    }

    public void send(Object object, HttpServletResponse resp, int status) throws IOException {
        String json = objectMapper.writeValueAsString(object);
        resp.setStatus(status);
        resp.getWriter().write(json);
    }

    public <T> T get(BufferedReader object, Class<T> clazz) throws IOException {
        return objectMapper.readValue(object, clazz);
    }
}
