package com.rajit.worldheritages.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rajit.worldheritages.data.model.FavouriteEntity

@Composable
fun FavouriteListView(
    favouriteListState: State<List<FavouriteEntity>>,
    onClick: (FavouriteEntity) -> Unit,
    onLongClick: (FavouriteEntity) -> Unit
) {

    val mContext = LocalContext.current.applicationContext
    val favouriteList = favouriteListState.value

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(favouriteList.size) { index ->

            val currentFavouriteItem = favouriteList[index]

            FavouriteItemView(
                currentFavouriteItem,
                onItemClicked = {
                    onClick(it)
                },
                onItemLongClicked = {
                    onLongClick(it)
                    Toast.makeText(mContext, "Deleted from Favourites", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

}