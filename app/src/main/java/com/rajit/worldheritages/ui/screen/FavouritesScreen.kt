package com.rajit.worldheritages.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.rajit.worldheritages.data.model.FavouriteEntity
import com.rajit.worldheritages.ui.components.FavouriteListView
import com.rajit.worldheritages.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouritesScreen(
    mainViewModel: MainViewModel = koinViewModel(),
    onItemClicked: (FavouriteEntity) -> Unit,
    onItemLongClicked: (FavouriteEntity) -> Unit
) {

    var favouritesList by remember {
        mutableStateOf(
            mainViewModel.fetchAllFavourites()
        )
    }

    LaunchedEffect(Unit) {
        favouritesList = mainViewModel.fetchAllFavourites()
    }

    FavouriteListView(
        favouriteListState = favouritesList.collectAsState(initial = emptyList()),
        onClick = onItemClicked,
        onLongClick = onItemLongClicked
    )
}