package com.assesment.weatherapp.ui.model

data class WeatherUIModel(
    val iconUrl: String,
    val countryFlagUrl: String = "http://openweathermap.org/images/flags/in.png",
    val temperature: String,
    val howsWeather: String,
    val address: String
)