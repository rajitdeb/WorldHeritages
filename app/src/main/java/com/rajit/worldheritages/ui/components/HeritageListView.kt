package com.rajit.worldheritages.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.rajit.worldheritages.data.model.HeritageEntity

@Composable
fun HeritageListView(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<HeritageEntity>?,
    onListItemClicked: (HeritageEntity) -> Unit
) {

    if (lazyPagingItems != null) {

        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->

                val currentHeritageItem = lazyPagingItems[index]

                if (currentHeritageItem != null) {
//                    Log.i("MainActivity", "WorldHeritageLiveData: ${currentHeritageItem.image}")
                    HeritageItemView(
                        heritage = lazyPagingItems[index],
                        onListItemClicked = onListItemClicked
                    )
                }

            }
        }
    }

}

@Preview
@Composable
fun HeritageListViewPreview() {
    HeritageListView(lazyPagingItems = null) {

    }
}