package com.example.weatherapp.Retrofit;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {

    private String temperature;
    private String icon;
    private String city;
    private String weatherType;
    private String pressure;
    private String tempHigh;
    private String tempLow;
    private String rain;
    private String windSpeed;
    private int humidity;
    private int Condition;




    public  static  WeatherData fromJson(JSONObject jsonObject)
    {

        try {
            WeatherData weatherData = new WeatherData();
            weatherData.city = jsonObject.getString("name");
            weatherData.Condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.weatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            weatherData.icon = updateWeatherIcon(weatherData.Condition);
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            weatherData.humidity = jsonObject.getJSONObject("main").getInt("humidity");
            weatherData.pressure = jsonObject.getJSONObject("main").getString("pressure");
            weatherData.windSpeed = jsonObject.getJSONObject("wind").getString("speed");
            double highResult = jsonObject.getJSONObject("main").getDouble("temp_max")-273.15;
            double lowResult = jsonObject.getJSONObject("main").getDouble("temp_min")-273.15;


            int roundedTempHigh = (int) Math.rint(highResult);
            weatherData.tempHigh = Integer.toString(roundedTempHigh);

            int roundedTempLow = (int) Math.rint(lowResult);
            weatherData.tempLow = Integer.toString(roundedTempLow);

            int roundedValue = (int) Math.rint(tempResult);
            weatherData.temperature = Integer.toString(roundedValue);

            Log.d("WeatherApp", "Temperature: " + weatherData.getTemperature());
            Log.d("WeatherApp", "Icon: " + weatherData.getIcon());
            Log.d("WeatherApp", "City: " + weatherData.getCity());
            Log.d("WeatherApp", "Weather Type: " + weatherData.getWeatherType());
            Log.d("WeatherApp", "Condition: " + weatherData.getCondition());
            Log.d("WeatherApp", "Humidity: " + weatherData.getHumidity());
            Log.d("WeatherApp", "Wind Speed: " + weatherData.getWindSpeed());
            Log.d("WeatherApp", "Temp High: " + weatherData.getTempHigh());
            Log.d("WeatherApp", "Temp Low: " + weatherData.getTempLow());
            Log.d("WeatherApp", "Pressure: " + weatherData.getPressure());


            return weatherData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String updateWeatherRain(double rainResult) {
        if (rainResult <= 2.0) {
            return "20";  // Low chance
        } else if (rainResult <= 5.0) {
            return "50";  // Moderate chance
        } else {
            return "80";  // High chance
        }
    }

    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition<=300) {
            return "storm";
        }
        else if (condition >= 300 && condition<=500) {
            return "windy";
        }
        else if (condition >= 500 && condition<=600) {
            return "rainy";
        }
        else if (condition >= 600 && condition <= 700) {
            return "snowy";
        }
        else if (condition >= 701 && condition<=771) {
            return "cloud";
        }
        else if (condition >= 772 && condition<=800) {
            return "cloudy";
        }
        else if (condition==800) {
            return "sun";
        }
        else if (condition >= 801 && condition <=804) {
            return "cloudy";
        }
        else if (condition >= 900 && condition <= 902) {
            return "storm";
        }
        else if (condition==903) {
            return "snowy";
        }
        else if (condition==904) {
            return "sun";
        }
        else if (condition >= 905 && condition <= 1000) {
            return "storm";
        }

        return "dunno";
    }

    public String getTemperature() {
        return temperature + "°";
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public int getCondition() {
        return Condition;
    }

    public void setCondition(int condition) {
        Condition = condition;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed + " km/h";
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getTempHigh() {
        return tempHigh;
    }

    public void setTempHigh(String tempHigh) {
        this.tempHigh = tempHigh;
    }

    public String getTempLow() {
        return tempLow;
    }

    public void setTempLow(String tempLow) {
        this.tempLow = tempLow;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}
