package com.ziru.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static String now(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
