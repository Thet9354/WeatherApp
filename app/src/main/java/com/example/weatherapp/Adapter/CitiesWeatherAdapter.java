package com.example.weatherapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.Retrofit.WeatherData;
import com.example.weatherapp.Retrofit.WeatherModel;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CitiesWeatherAdapter extends RecyclerView.Adapter<CitiesWeatherAdapter.ViewHolder> {

    private ArrayList<WeatherModel> weatherList;

    public CitiesWeatherAdapter(ArrayList<WeatherModel> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public CitiesWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesWeatherAdapter.ViewHolder holder, int position) {
        WeatherModel weather = weatherList.get(position);
        holder.txtView_cities.setText(weather.getLocation());

        String temperatureString = weather.getTemperature();
        double temperatureCelsius = Double.parseDouble(temperatureString) - 273.15;
        int roundedTemperature = (int) Math.round(temperatureCelsius);

        String temp_max = weather.getTemp_max();
        double tempMaxCelsius = Double.parseDouble(temp_max) - 273.15;
        int roundedTempMax = (int) Math.round(tempMaxCelsius);

        String temp_min = weather.getTemp_min();
        double tempMinCelsius = Double.parseDouble(temp_min) - 273.15;
        int roundedTempMin = (int) Math.round(tempMinCelsius);

        holder.txtView_cityTemp.setText(String.valueOf(roundedTemperature) + "°");
        holder.txtView_weatherDescription.setText(weather.getWeatherDescription());
        holder.txtView_HL.setText("H:" + roundedTempMax + "° " + "L:" + roundedTempMin + "°");

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private androidx.cardview.widget.CardView cv_citiesWeather;
        private TextView txtView_cities, txtView_weatherDescription, txtView_cityTemp, txtView_HL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_citiesWeather = itemView.findViewById(R.id.cv_citiesWeather);

            txtView_cities = itemView.findViewById(R.id.txtView_cities);
            txtView_weatherDescription = itemView.findViewById(R.id.txtView_weatherDescription);
            txtView_cityTemp = itemView.findViewById(R.id.txtView_cityTemp);
            txtView_HL = itemView.findViewById(R.id.txtView_HL);
        }
    }
}

