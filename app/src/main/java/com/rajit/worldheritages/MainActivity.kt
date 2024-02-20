package com.rajit.worldheritages

import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.gson.Gson
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.data.model.toFavouriteEntity
import com.rajit.worldheritages.ui.components.HeritageDetailScreen
import com.rajit.worldheritages.ui.components.HeritageListView
import com.rajit.worldheritages.ui.components.MyBottomSheet
import com.rajit.worldheritages.ui.theme.WorldHeritagesTheme
import com.rajit.worldheritages.ui.util.HeritageNavArgType
import com.rajit.worldheritages.util.Constants
import com.rajit.worldheritages.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
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
                    Navigation(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun Navigation(
    mainViewModel: MainViewModel = koinViewModel()
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable(route = "main") {
            MyScaffold(mainViewModel = mainViewModel) {
                val json = Uri.encode(Gson().toJson(it))
                navController.navigate("detail/$json")
            }
        }

        composable(
            route = "detail/{heritage}",
            arguments = listOf(
                navArgument("heritage") {
                    type = HeritageNavArgType()
                }
            )
        ) { navBackStackEntry ->
            val heritageModel =
                navBackStackEntry.arguments?.getParcelable<HeritageEntity>("heritage")

            if (heritageModel != null) {

                var isFavorite by remember { mutableStateOf(false) }

                LaunchedEffect(key1 = Unit) {
                    withContext(Dispatchers.IO) {
                        isFavorite = mainViewModel.fetchFavouriteByID(heritageModel.id)
                    }
                }

                HeritageDetailScreen(
                    isFavourite = isFavorite,
                    heritage = heritageModel,
                    onBackClicked = {
                        navController.navigate("main") {
                            popUpTo("main") {
                                inclusive = true
                            }
                        }
                    },
                    onFabClicked = {
                        if (it) { // If Already Bookmarked, Delete Favourite
                            mainViewModel.deleteFavourite(heritageModel.toFavouriteEntity())
                        } else { // Otherwise Save to Favourites
                            mainViewModel.saveToFavourites(heritageModel.toFavouriteEntity())
                        }
                    }
                )
            }
        }
    }

}

// My Main Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(
    mainViewModel: MainViewModel,
    onListItemClicked: (HeritageEntity) -> Unit
) {

    val mSheetScaffoldState = rememberBottomSheetScaffoldState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val countryTagPref = mainViewModel.getCountryAndTagPreference()

    var heritageList by remember {

        mutableStateOf(
            mainViewModel.fetchAllHeritagesByFilter(
                if (countryTagPref.first != "ALL") countryTagPref.first else "",
                if (countryTagPref.second != "All") countryTagPref.second else ""
            )
        )
    }

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
                HeritageListView(
                    lazyPagingItems = heritageList.collectAsLazyPagingItems(),
                    onListItemClicked = onListItemClicked
                )
            }
        }

    }

}

