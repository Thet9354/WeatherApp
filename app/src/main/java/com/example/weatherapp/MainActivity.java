package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.Adapter.CitiesWeatherAdapter;
import com.example.weatherapp.Retrofit.ApiClient;
import com.example.weatherapp.Retrofit.OpenWeatherMapService;
import com.example.weatherapp.Retrofit.WeatherData;
import com.example.weatherapp.Retrofit.WeatherModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {

    private TextView txtView_city, txtView_date, txtView_weatherDesc, txtView_temperature, txtView_minmaxTemp, txtView_pressure, txtView_windSpeedPercentage, txtView_humidityPercentage;

    private ImageView imgView_weather;

    private androidx.recyclerview.widget.RecyclerView rv_otherCities;

    private CitiesWeatherAdapter citiesWeatherAdapter;

    private OpenWeatherMapService openWeatherMapService;

    private String apiKey = "36f6d39db47db1a4c0645385e6b8e5f0";
    private String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather/";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    private ArrayList<WeatherModel> weatherList;


    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();

        getWeatherForCurrentLocation();
    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(mContext.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                String lat = String.valueOf(location.getLatitude());
                String lon = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("appid", apiKey);

                networking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                // Not able to get location
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    private void networking(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(MainActivity.this,"Data Get Success",Toast.LENGTH_SHORT).show();

                WeatherData weatherD=WeatherData.fromJson(response);
                updateUI(weatherD);


                // super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void updateUI(WeatherData weather) {

        txtView_temperature.setText(weather.getTemperature());
        txtView_city.setText(weather.getCity());
        txtView_weatherDesc.setText(weather.getWeatherType());
        int resourceID = getResources().getIdentifier(weather.getIcon(), "drawable", getPackageName());
        imgView_weather.setImageResource(resourceID);
        txtView_humidityPercentage.setText(weather.getHumidity() + "%");
        txtView_windSpeedPercentage.setText(weather.getWindSpeed());
        txtView_minmaxTemp.setText("H:" + weather.getTempHigh() + " L:" + weather.getTempLow());
        txtView_pressure.setText(weather.getPressure());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CODE) {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "Location successful", Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            } else {
                // User denied permission
            }
        }
    }

    private void initWidget() {

        //TextView
        txtView_city = findViewById(R.id.txtView_city);
        txtView_date = findViewById(R.id.txtView_date);
        txtView_weatherDesc = findViewById(R.id.txtView_weatherDesc);
        txtView_temperature = findViewById(R.id.txtView_temperature);
        txtView_minmaxTemp = findViewById(R.id.txtView_minmaxTemp);
        txtView_pressure = findViewById(R.id.txtView_pressure);
        txtView_windSpeedPercentage = findViewById(R.id.txtView_windSpeedPercentage);
        txtView_humidityPercentage = findViewById(R.id.txtView_humidityPercentage);

        //ImageView
        imgView_weather = findViewById(R.id.imgView_weather);

        //RecyclerView
        rv_otherCities = findViewById(R.id.rv_otherCities);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getCurrentDate();
        }

        initRecView();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getCurrentDate() {
        LocalDate currentDate = LocalDate.now();

        // Format the date using a custom pattern and Locale
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMMM dd", Locale.ENGLISH);
        String formattedDate = currentDate.format(formatter);

        txtView_date.setText(formattedDate);
    }

    private void initRecView() {

        weatherList = new ArrayList<>();
        citiesWeatherAdapter = new CitiesWeatherAdapter(weatherList);

        int spaceInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        rv_otherCities.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        rv_otherCities.setLayoutManager(new LinearLayoutManager(this));
        rv_otherCities.setAdapter(citiesWeatherAdapter);

        // Check and display saved data if available
        // Otherwise, make a network call to get fresh data
        // Update the UI accordingly
        fetchWeatherForCities();

    }

    private void makeNetworkRequest(String city) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, apiUrl, null, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleNetworkResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getSavedWeatherResponse(MainActivity.this);
                        String errorMessage = "Error fetching data: " + error.getMessage();
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        System.out.println("Failed: " + errorMessage);
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void handleNetworkResponse(JSONObject response) {
        try {
            // Parse the JSON response and update the UI
            String location = response.getString("name");
            String temperature = response.getJSONObject("main").getString("temp");
            String weatherDescription = response.getJSONArray("weather").getJSONObject(0).getString("description");
            String tempMin = response.getJSONObject("main").getString("temp_min");
            String tempMax = response.getJSONObject("main").getString("temp_max");

            WeatherModel weather = new WeatherModel(location, temperature, weatherDescription, tempMin, tempMax);

            // Save the response with the current timestamp
            saveWeatherResponse(weather);

            // Add the weather data to the list for the bonus task
            weatherList.add(weather);
            citiesWeatherAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveWeatherResponse(WeatherModel weather) {
        String timestamp = DateFormat.getDateTimeInstance().format(new Date());
        Log.d("WeatherApp", "Saving response at " + timestamp);

        SharedPreferences preferences = MainActivity.this.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("location", weather.getLocation());
        editor.putString("temperature", weather.getTemperature());
        editor.putString("weatherDescription", weather.getWeatherDescription());
        editor.putString("tempMin", weather.getTemp_min());
        editor.putString("tempMax", weather.getTemp_max());
        editor.putString("timestamp", timestamp);

        editor.apply();
    }

    private WeatherModel getSavedWeatherResponse(Context context) {
        // Retrieve weather information from SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE);

        String location = preferences.getString("location", "");
        String temperature = preferences.getString("temperature", "");
        String weatherDescription = preferences.getString("weatherDescription", "");
        String tempMin = preferences.getString("tempMin", "");
        String tempMax = preferences.getString("tempMax", "");
        String timestamp = preferences.getString("timestamp", "");

        return new WeatherModel(location, temperature, weatherDescription, tempMin, tempMax);
    }

    private void fetchWeatherForCities() {
        ArrayList<String> cities = new ArrayList<>();
        cities.add("New York");
        cities.add("Singapore");
        cities.add("Mumbai");
        cities.add("Delhi");
        cities.add("Sydney");
        cities.add("Melbourne");

        for (String city : cities) {
            makeNetworkRequestForCity(city);
        }
    }

    private void makeNetworkRequestForCity(String city) {
        makeNetworkRequest(city);
    }

}



