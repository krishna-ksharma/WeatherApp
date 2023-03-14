package com.assesment.weatherapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assesment.weatherapp.ui.compose.SearchWidgetCompose
import com.assesment.weatherapp.ui.compose.SearchWidgetState
import com.assesment.weatherapp.ui.compose.SearchWidgetViewModel
import com.assesment.weatherapp.ui.compose.WeatherResultCompose

@Composable
fun MainScreen() {
    val searchWidgetViewModel: SearchWidgetViewModel = viewModel(SearchWidgetViewModel::class.java)
    val searchWidgetState by searchWidgetViewModel.searchWidgetState
    val searchTextState by searchWidgetViewModel.searchTextState
    var query by remember { mutableStateOf("") }
    val viewModel: WeatherHomeViewModel = viewModel(WeatherHomeViewModel::class.java)
    val weather = viewModel.state.observeAsState()
    LaunchedEffect(key1 = query) {
        viewModel.findWeather(query = query)
    }
    Scaffold(
        topBar = {
            SearchWidgetCompose(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    searchWidgetViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    searchWidgetViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    query = it;
                },
                onSearchTriggered = {
                    searchWidgetViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        }
    ) {
        it.calculateBottomPadding();
        WeatherResultCompose(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            weatherUIModel = weather.value
        )
    }
}