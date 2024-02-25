package com.rajit.worldheritages.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.ui.components.HeritageItemView
import com.rajit.worldheritages.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    onItemClicked: (HeritageEntity) -> Unit,
    mainViewModel: MainViewModel = koinViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {

        var searchQuery by remember {
            mutableStateOf("Nothing")
        }

        var heritageList by remember {
            mutableStateOf(
                mainViewModel.fetchAllHeritagesBySearchQuery(searchQuery)
            )
        }

        LaunchedEffect(searchQuery) {
            heritageList = mainViewModel.fetchAllHeritagesBySearchQuery(searchQuery)
        }

        // Search View
        MySearchView(
            heritageList.collectAsLazyPagingItems(),
            onSearchClicked = {
                searchQuery = it
            }
        ) { onItemClicked(it) }
    }
}

@Composable
fun MySearchView(
    heritageListState: LazyPagingItems<HeritageEntity>,
    onSearchClicked: (String) -> Unit,
    onItemClicked: (HeritageEntity) -> Unit
) {

    var query by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        TextField(
            value = query,
            onValueChange = {
                query = it
                onSearchClicked(it.text)
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Type Something...")
            },
            trailingIcon = {
                if (query.text.isNotEmpty()) {
                    IconButton(onClick = { query = TextFieldValue("") }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(query.text)
                }
            ),
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            items(
                count = heritageListState.itemCount,
                key = heritageListState.itemKey { it.id }
            ) { index ->
                HeritageItemView(heritage = heritageListState[index]) {
                    onItemClicked(it)
                }
            }
        }
    }
}
