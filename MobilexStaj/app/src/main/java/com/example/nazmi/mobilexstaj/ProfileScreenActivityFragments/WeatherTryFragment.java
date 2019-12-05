package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nazmi.mobilexstaj.Common.Common;
import com.example.nazmi.mobilexstaj.R;
import com.example.nazmi.mobilexstaj.Retrofit.IOpenWeatherMap;
import com.example.nazmi.mobilexstaj.Retrofit.RetrofitClient;
import com.example.nazmi.mobilexstaj.model.WeatherResult;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class WeatherTryFragment extends Fragment {
    private View weatherTryView;
    static WeatherTryFragment instance;

    ImageView ivWeather;
    TextView txtCityName, txtHumidity, txtSunrise, txtSunset, txtPressure, txtTemperature, txtDescription, txtDateTime, txtWind, txtGeoCoord;
    LinearLayout weatherPanel;
    ProgressBar loading;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap myService;


    public static WeatherTryFragment getInstance(){
        if (instance==null)
            instance=new WeatherTryFragment();
        return instance;
    }

    public WeatherTryFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        myService = retrofit.create(IOpenWeatherMap.class);

    }

    //Inflate
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        weatherTryView = inflater.inflate(R.layout.weather_try_fragment, container, false);

        ivWeather = weatherTryView.findViewById(R.id.img_weather);
        txtCityName = weatherTryView.findViewById(R.id.txt_city_name);
        txtHumidity = weatherTryView.findViewById(R.id.txt_humidity);
        txtSunrise = weatherTryView.findViewById(R.id.txt_sunrise);
        txtSunset = weatherTryView.findViewById(R.id.txt_sunset);
        txtPressure = weatherTryView.findViewById(R.id.txt_pressure);
        txtTemperature = weatherTryView.findViewById(R.id.txt_temperature);
        txtDescription = weatherTryView.findViewById(R.id.txt_description);
        txtDateTime = weatherTryView.findViewById(R.id.txt_date_time);
        txtWind = weatherTryView.findViewById(R.id.txt_wind);
        txtGeoCoord = weatherTryView.findViewById(R.id.txt_geo_code);

        weatherPanel = weatherTryView.findViewById(R.id.weather_panel);
        loading= weatherTryView.findViewById(R.id.loading);

        getWeatherInformation();

        return weatherTryView;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(myService.getWeatherByLatLng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        //Load Image
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                        .append(".png").toString()).into(ivWeather);

                        //Load Information
                        txtCityName.setText(weatherResult.getName());
                        txtDescription.setText(new StringBuilder("Weather in ")
                        .append(weatherResult.getName()).toString());
                        txtTemperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
                        txtDateTime.setText(Common.convertUnixToDate(weatherResult.getDt()));
                        txtPressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());
                        txtHumidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" %").toString());
                        txtSunrise.setText(Common.convertUnixHour(weatherResult.getSys().getSunrise()));
                        txtSunset.setText(Common.convertUnixHour(weatherResult.getSys().getSunset()));
                        txtGeoCoord.setText(new StringBuilder("[").append(weatherResult.getCoord().toString()).append("]").toString());
                        txtWind.setText(new StringBuilder("Speed : "+weatherResult.getWind().getSpeed()).append(" & ").append("Direction : "+weatherResult.getWind().getDeg()));

                        //Display Panel
                        weatherPanel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
                })

        );
    }
}
