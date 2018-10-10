package br.com.andersonv.famousmovies.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static Date convertStringToDate(String date, String formatDate){
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatDate, Locale.getDefault());
            return new Date(format.parse(date).getTime());
        }catch (Exception e){
            return null;
        }
    }
}
