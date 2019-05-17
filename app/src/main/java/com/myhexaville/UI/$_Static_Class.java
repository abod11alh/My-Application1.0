package com.myhexaville.UI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

 public class $_Static_Class {
    public static String getCurrentDate(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);//dd/MM/yyyy
        Date now = new Date();
        return sdfDate.format(now);
    }
}
