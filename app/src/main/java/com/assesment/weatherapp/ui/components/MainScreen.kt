package com.assesment.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.assesment.weatherapp.data.api.model.Coord
import com.assesment.weatherapp.ui.compose.SearchBar
import com.assesment.weatherapp.ui.model.WeatherUIModel
import com.assesment.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    WeatherAppTheme {
        ScreenContent(modifier = modifier)
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    val viewModel: WeatherHomeViewModel = viewModel(WeatherHomeViewModel::class.java)
    val weather = viewModel.state.observeAsState()
    var query by remember { mutableStateOf("") }
    LaunchedEffect(key1 = query) {
        viewModel.findWeather(query = query)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchBar(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) { input ->
            query = input
        }
        SearchResult(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            weatherUIModel = weather.value
        )
    }
}

@Composable
fun SearchResult(modifier: Modifier = Modifier, weatherUIModel: WeatherUIModel?) {
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
                    Text(text = weather.address, style = MaterialTheme.typography.bodyMedium)
                    AsyncImage(
                        modifier = Modifier.height(24.dp),
                        model = weather.countryFlagUrl,
                        contentDescription = null
                    )
                    Text(
                        text = weather.howsWeather,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = "Geo coords [${weather.coords.lat},${weather.coords.lat}]",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    SearchResult(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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