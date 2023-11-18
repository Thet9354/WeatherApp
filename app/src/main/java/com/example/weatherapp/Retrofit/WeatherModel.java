package com.example.weatherapp.Retrofit;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherModel {
    private String location;
    private String temperature;
    private String weatherDescription;

    private String temp_min;

    private String temp_max;


    public WeatherModel(String location, String temperature, String weatherDescription, String temp_min, String temp_max) {
        this.location = location;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public String getLocation() {
        return location;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }
}


