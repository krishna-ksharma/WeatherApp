package com.assesment.weatherapp.data.api

import com.assesment.weatherapp.data.api.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun fetchWeather(query: String): Response<Weather> = withContext(Dispatchers.IO) {
        weatherApi.fetchWeather(query)
    }
}