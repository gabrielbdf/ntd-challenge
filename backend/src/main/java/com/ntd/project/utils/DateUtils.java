package com.ntd.project.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
