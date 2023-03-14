package com.assesment.weatherapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.assesment.weatherapp.data.api.model.Coord
import com.assesment.weatherapp.ui.model.WeatherUIModel


@Composable
fun WeatherResultCompose(modifier: Modifier = Modifier, weatherUIModel: WeatherUIModel?) {
    weatherUIModel?.let { weather ->
        Row(modifier = modifier) {
            AsyncImage(
                model = weather.iconUrl,
                contentDescription = null,
                modifier = modifier
            )
            Column() {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(text = weather.address, style = MaterialTheme.typography.h6)
                    AsyncImage(
                        modifier = Modifier.height(24.dp),
                        model = weather.countryFlagUrl,
                        contentDescription = null
                    )
                    Text(
                        text = weather.howsWeather,
                        style = MaterialTheme.typography.body1
                    )
                }
                Text(
                    text = "Geo coords [${weather.coords.lat},${weather.coords.lat}]",
                    style = MaterialTheme.typography.body1,
                    modifier = modifier,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    WeatherResultCompose(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        weatherUIModel = WeatherUIModel(
            iconUrl = "https://openweathermap.org/img/wn/04d@2x.png",
            countryFlagUrl = "https://openweathermap.org/images/flags/gb.png",
            temperature = "274.21",
            howsWeather = "broken clouds",
            address = "London",
            coords = Coord(lon = 81.3, lat = 24.5333)
        )
    )
}