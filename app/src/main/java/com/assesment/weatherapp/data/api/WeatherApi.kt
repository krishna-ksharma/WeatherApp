package com.assesment.weatherapp.data.api

import com.assesment.weatherapp.data.api.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(ApiConstants.API_ENDPOINT)
    suspend fun fetchWeather(@Query("q") query: String, @Query("APPID") appId: String): Response<Weather>
}