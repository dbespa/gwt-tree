package com.example.myapp.client;

import com.google.gwt.core.client.GWT;

public class StringUtils {

    public StringUtils() {
        // do nothing
    }

    public static Integer parseIntQuiet(String str) {
        try {
            Integer integer = Integer.parseInt(str);
            return integer;

        } catch (Exception e) {
            GWT.log("Ошибка конвертации: " + e.getLocalizedMessage());
            return null;
        }
    }
}
