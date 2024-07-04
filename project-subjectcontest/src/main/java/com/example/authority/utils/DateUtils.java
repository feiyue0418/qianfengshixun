package com.example.authority.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getNowDate(){
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
