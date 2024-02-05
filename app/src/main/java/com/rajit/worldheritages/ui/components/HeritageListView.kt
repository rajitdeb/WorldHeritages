package com.rajit.worldheritages.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.rajit.worldheritages.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HeritageListView(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel? = koinViewModel()
) {

    if (mainViewModel != null) {

        val lazyPagingItems = mainViewModel.heritageList.collectAsLazyPagingItems()

        LaunchedEffect(key1 = lazyPagingItems) {
            mainViewModel.fetchAllHeritages()
        }

        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(lazyPagingItems.itemCount) { index ->
//            Log.i("MainActivity", "WorldHeritageLiveData: ${lazyPagingItems[index]}")
                HeritageItemView(heritage = lazyPagingItems[index])
            }
        }
    }

}

@Preview
@Composable
fun HeritageListViewPreview() {
    HeritageListView()
}