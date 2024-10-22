package ru.viktorgezz.util;

import com.sun.source.tree.Tree;
import jakarta.servlet.http.HttpServletRequest;

public class PathInfo {

    public String getInfoWithoutSlash(HttpServletRequest req) {
        return req.getPathInfo().substring(1);
    }

    public String getFirstCodeOutOfTwo(HttpServletRequest req) {
        return getInfoWithoutSlash(req).substring(0, 3);
    }

    public String getSecondCodeOutOfTwo(HttpServletRequest req) {
        return getInfoWithoutSlash(req).substring(3, 6);
    }
}
