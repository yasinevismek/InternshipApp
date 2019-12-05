package com.example.nazmi.mobilexstaj.Retrofit;
/**
 * Created by Nazmican GÖKBULUT
 */
import com.example.nazmi.mobilexstaj.model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
//Todo: Retrofit Service
public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);
}
