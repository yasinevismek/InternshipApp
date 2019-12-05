package com.example.nazmi.mobilexstaj.Common;
/**
 * Created by Nazmican GÖKBULUT
 */
import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    //Todo: Identify Open Weather App for Key
    public static final String APP_ID = "5e1537a55a1ac4b0cf0ad0fa94b86c58";
    public static Location current_location = null;

    //Todo: Date Convert
    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    //Todo: Hour Convert
    public static String convertUnixHour(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatted = sdf.format(date);
        return formatted;
    }

    //Todo: http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=5e1537a55a1ac4b0cf0ad0fa94b86c58
}
