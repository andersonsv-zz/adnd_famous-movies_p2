package br.com.andersonv.famousmovies.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date convertStringToDate(String date, String formatDate){
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatDate);
            return new Date(format.parse(date).getTime());
        }catch (Exception e){
            return null;
        }
    }
}
