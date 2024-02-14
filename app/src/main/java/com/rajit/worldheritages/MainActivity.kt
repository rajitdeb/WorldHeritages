package com.rajit.worldheritages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.paging.compose.collectAsLazyPagingItems
import com.rajit.worldheritages.ui.components.HeritageListView
import com.rajit.worldheritages.ui.components.MyBottomSheet
import com.rajit.worldheritages.ui.theme.WorldHeritagesTheme
import com.rajit.worldheritages.util.Constants
import com.rajit.worldheritages.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    // injecting viewModel instance using koin
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {

            WorldHeritagesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScaffold(mainViewModel = mainViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(mainViewModel: MainViewModel) {

    val mSheetScaffoldState = rememberBottomSheetScaffoldState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var heritageList by remember { mutableStateOf(mainViewModel.fetchAllHeritagesByFilter()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(Icons.Filled.FilterAlt, "Filter Heritage Sites")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        BottomSheetScaffold(
            scaffoldState = mSheetScaffoldState,
            sheetContent = {
                if (showBottomSheet) {
                    MyBottomSheet(
                        onSubmit = {

                            // Saving User Preferences
                            mainViewModel.saveCountryAndTagPreference(it.first, it.second)

                            if (it.first != Constants.DEFAULT_COUNTRY_FILTER
                                && it.second == Constants.DEFAULT_TAG_FILTER
                            ) { // Check if the Tag is DEFAULT

                                heritageList = mainViewModel
                                    .fetchAllHeritagesByFilter(country = it.first)

                            } else if (it.first == Constants.DEFAULT_COUNTRY_FILTER
                                && it.second != Constants.DEFAULT_TAG_FILTER
                            ) { // Check if the Country is DEFAULT

                                heritageList = mainViewModel
                                    .fetchAllHeritagesByFilter(tag = it.second)

                            } else if (it.first != Constants.DEFAULT_COUNTRY_FILTER
                                && it.second != Constants.DEFAULT_TAG_FILTER
                            ) { // Check if BOTH are NOT DEFAULT

                                heritageList = mainViewModel
                                    .fetchAllHeritagesByFilter(country = it.first, tag = it.second)

                            } else { // Both are set to Defaults, use the default parameters
                                heritageList = mainViewModel.fetchAllHeritagesByFilter()
                            }
                        },
                        onReset = {
                            // Resetting the User Country and Tag Preferences to Default
                            mainViewModel.resetUserCountryAndTagPreference()
                        },
                        onDismiss = { showBottomSheet = false }
                    )
                }
            },
            sheetPeekHeight = 0.dp
        ) {
            Box(Modifier.padding(paddingValues)) {
                HeritageListView(lazyPagingItems = heritageList.collectAsLazyPagingItems())
            }
        }

    }

}

