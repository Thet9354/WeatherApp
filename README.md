# Weather App

## Overview

The Weather App is an Android application developed in Java using Android Studio. It provides users with real-time weather forecast details for their current location and displays weather conditions for selected cities. The app fetches data from the OpenWeatherMap API in JSON format.

## Features

- Display current location's weather forecast details:
  - Pressure
  - Temperature
  - Weather condition
  - Min temperature
  - Max temperature
  - Wind speed
  - Humidity

- Display weather conditions for selected cities using RecyclerView and CardView:
  1. New York
  2. Singapore
  3. Mumbai
  4. Delhi
  5. Sydney
  6. Melbourne

- Save weather response for offline use:
  - The app saves the response with the current timestamp for the current location.
  - On subsequent application startups or in case of no internet, the saved data is displayed with the time when the data was received.

- Background network call for the latest weather data:
  - The app makes background network calls to get the latest weather data for the current location.

Google Drive Link: https://drive.google.com/drive/folders/1asUIPYSslp_Gw2hgSnT5bOwlh0gjZaQu?usp=share_link
