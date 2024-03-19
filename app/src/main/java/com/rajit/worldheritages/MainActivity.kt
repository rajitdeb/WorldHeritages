package com.rajit.worldheritages

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.data.model.toFavouriteEntity
import com.rajit.worldheritages.data.model.toHeritageEntity
import com.rajit.worldheritages.ui.components.HeritageDetailScreen
import com.rajit.worldheritages.ui.components.HeritageListView
import com.rajit.worldheritages.ui.components.MyBottomSheet
import com.rajit.worldheritages.ui.screen.FavouritesScreen
import com.rajit.worldheritages.ui.screen.SearchScreen
import com.rajit.worldheritages.ui.theme.WorldHeritagesTheme
import com.rajit.worldheritages.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

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

                    MyMainScaffold()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMainScaffold(mainViewModel: MainViewModel = koinViewModel()) {

    val mSheetScaffoldState = rememberBottomSheetScaffoldState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val countryTagPrefState = mainViewModel.countryTagPref.collectAsState()

    Scaffold(
        topBar = { MyTopAppBar(currentBackStackEntry, navController) },
        bottomBar = { MyBottomNavBar(currentBackStackEntry, navController) },
        floatingActionButton = {
            MyFloatingActionButton(
                currentBackStackEntry = currentBackStackEntry,
                onFilterClicked = { showBottomSheet = true }
            )
        },
    ) { contentPadding ->

        BottomSheetScaffold(
            scaffoldState = mSheetScaffoldState,
            sheetContent = {

                if (showBottomSheet) {

                    MyBottomSheet(
                        onSubmit = {
                            // Saving User Preferences
                            mainViewModel.saveCountryAndTagPreference(it.first, it.second)
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
            MyNavigation(
                countryTagPrefState = countryTagPrefState,
                contentPadding = contentPadding,
                navController = navController
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavHostController
) {
    val currentRoute = currentBackStackEntry?.destination?.route

    val topAppBarTitle = if (currentRoute != null && currentRoute.contains("detail")) {
        "Heritage Details"
    } else {
        "World Heritages"
    }

    Log.i("MainActivity", "TopAppBar: Current Route: $currentRoute")

    TopAppBar(
        title = { Text(topAppBarTitle) },
        navigationIcon = {
            if (topAppBarTitle == "Heritage Details") {
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Up Button"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.purple_500),
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun MyFloatingActionButton(
    currentBackStackEntry: NavBackStackEntry?,
    onFilterClicked: () -> Unit
) {

    val currentRoute = currentBackStackEntry?.destination?.route

    if (currentRoute != null && currentRoute.contains("main")) {
        FloatingActionButton(
            onClick = { onFilterClicked() }
        ) {
            Icon(
                imageVector = Icons.Filled.FilterAlt,
                contentDescription = "Fab Button"
            )
        }
    }
}

@Composable
fun MyBottomNavBar(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavHostController
) {
    val currentRoute = currentBackStackEntry?.destination?.route

    if (currentRoute != null && !currentRoute.toString().contains("detail")) {
        NavigationBar {

            // Home
            NavigationBarItem(
                selected = currentRoute == "main" || currentRoute == null,
                onClick = {
                    if (currentRoute != "main") {
                        navController.navigate("main")
                    }
                },
                icon = {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                },
                label = { Text(text = "Home") }
            )

            // Search
            NavigationBarItem(
                selected = currentRoute == "search",
                onClick = {
                    if (currentRoute != "search") {
                        navController.navigate("search")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                },
                label = { Text(text = "Search") }
            )

            // Favourites
            NavigationBarItem(
                selected = currentRoute == "favourites",
                onClick = {
                    if (currentRoute != "favourites") {
                        navController.navigate("favourites")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = "Favourites"
                    )
                },
                label = { Text(text = "Favourites") }
            )
        }
    }
}

@Composable
fun MyNavigation(
    countryTagPrefState: State<Pair<String, String>>,
    contentPadding: PaddingValues,
    navController: NavHostController,
    mainViewModel: MainViewModel = koinViewModel()
) {

    Box(modifier = Modifier.padding(contentPadding)) {
        NavHost(navController = navController, startDestination = "main") {

            // HomeScreen
            composable(route = "main") {

                HomeScreen(
                    countryTagPrefState.value,
                    onListItemClicked = {

                        // Setting the HeritageEntity in MainViewModel for HeritageDetail Screen to retrieve
                        mainViewModel.heritageDetailState.value = it

                        navController.navigate(route = "detail")

//                        // This is done because Navigation Component can't directly use URL as argument
//                        // We need to encode the URL before passing it as argument
//                        // Decoding is taken care of by Compose-Navigation
//                        val encodedPageURL = Constants.encodeURLForNavigation(it.page)
//                        val encodedImageURL = Constants.encodeURLForNavigation(it.image)

//                        navController.navigate(
//                            route = "detail/${it.id}/${it.year}/${it.target}/${it.name}/${it.type}/${it.region}/${it.regionLong}/${it.coordinates}/${it.lat}/${it.lng}/$encodedPageURL/$encodedImageURL/${it.imageAuthor}/${formattedShortInfo}/$formattedLongInfo"
//                        )
                    }
                )
            }

            // Search Screen
            composable(route = "search") {
                SearchScreen(
                    onItemClicked = {

                        // Setting the HeritageEntity in MainViewModel for HeritageDetail Screen to retrieve
                        mainViewModel.heritageDetailState.value = it

                        navController.navigate(route = "detail")

                    }
                )
            }

            // Favourites Screen
            composable(route = "favourites") {
                FavouritesScreen(
                    onItemClicked = {

                        // Converting FavouriteEntity to HeritageEntity
                        val heritage = it.toHeritageEntity()

                        // Setting the HeritageEntity in MainViewModel for HeritageDetail Screen to retrieve
                        mainViewModel.heritageDetailState.value = heritage

                        navController.navigate(route = "detail")
                    },
                    onItemLongClicked = { mainViewModel.deleteFavourite(it) }
                )
            }

            // Heritage Detail Screen
            composable(route = "detail") {

                var heritage by remember {
                    mutableStateOf<HeritageEntity?>(null)
                }

                var isFavorite by remember { mutableStateOf(false) }

                LaunchedEffect(key1 = Unit) {
                    withContext(Dispatchers.IO) {
                        heritage = mainViewModel.heritageDetailState.value
                        if (heritage != null) {
                            isFavorite = mainViewModel.fetchFavouriteByID(heritage!!.id)
                        }
                    }
                }

                if (heritage != null) {
                    HeritageDetailScreen(
                        isFavourite = isFavorite,
                        heritage = heritage!!,
                        onFabClicked = {
                            if (it) { // If Already Bookmarked, Delete Favourite
                                mainViewModel.deleteFavourite(heritage!!.toFavouriteEntity())
                            } else { // Otherwise Save to Favourites
                                mainViewModel.saveToFavourites(heritage!!.toFavouriteEntity())
                            }
                        }
                    )
                }

            }
        }
    }
}


@Composable
fun HomeScreen(
    countryTagPref: Pair<String, String>,
    mainViewModel: MainViewModel = koinViewModel(),
    onListItemClicked: (HeritageEntity) -> Unit
) {

    var heritageList by remember {
        mutableStateOf(
            mainViewModel.fetchAllHeritagesByFilter(
                country = if (countryTagPref.first != "ALL") countryTagPref.first else "",
                tag = if (countryTagPref.second != "All") countryTagPref.second else ""
            )
        )
    }

    LaunchedEffect(key1 = countryTagPref) {
        heritageList = mainViewModel.fetchAllHeritagesByFilter(
            country = if (countryTagPref.first != "ALL") countryTagPref.first else "",
            tag = if (countryTagPref.second != "All") countryTagPref.second else ""
        )
    }

    val heritagePagingListItems = heritageList.collectAsLazyPagingItems()

    HeritageListView(
        lazyPagingItems = heritagePagingListItems,
        onListItemClicked = onListItemClicked
    )

}