package ru.viktorgezz.util;

public class BdConfig {

    static {
        loadDriver();
        URL = PropertiesUtil.getUrl();
    }

    public final static String URL;

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
