package com.assesment.weatherapp.ui.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assesment.weatherapp.data.api.WeatherRepository
import com.assesment.weatherapp.data.api.model.Weather
import com.assesment.weatherapp.ui.model.WeatherUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _state = MutableLiveData<WeatherUIModel>()
    val state: LiveData<WeatherUIModel>
        get() = _state

    fun findWeather(query: String = "London,uk") {
        viewModelScope.launch {
            val result = weatherRepository.fetchWeather(query = query)
            if (result.isSuccessful) {
                val weather = result.body()
                weather?.let {weather->
                    val uiModel = WeatherUIModel(
                        iconUrl = "https://openweathermap.org/img/wn/${
                            weather.weather[0].icon
                        }@2x.png",
                        countryFlagUrl = "https://openweathermap.org/images/flags/${weather.sys.country.lowercase()}.png",
                        temperature = weather.main.feels_like.toString(),
                        howsWeather = weather.weather[0].description,
                        address = "${weather.name}, ${weather.sys.country}"
                    )
                    _state.value = uiModel
                }
            }
        }
    }
}

sealed class WeatherUiState {
    data class Success(val weather: List<Weather>) : WeatherUiState()
    data class Error(val exception: Throwable) : WeatherUiState()
}