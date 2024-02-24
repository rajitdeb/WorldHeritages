package com.rajit.worldheritages.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rajit.worldheritages.R
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.util.Constants
import com.rajit.worldheritages.util.CustomTab

@Composable
fun HeritageDetailScreen(
    isFavourite: Boolean,
    heritage: HeritageEntity,
    onFabClicked: (Boolean) -> Unit
) {

    val context = LocalContext.current.applicationContext

    Scaffold(
        floatingActionButton = {
            SaveToFavouriteFabButton(isFavourite, onFabClicked)
        }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier.padding(contentPadding)
        ) {

            // Heritage Image
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(heritage.image)
                        .crossfade(enable = true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.error_placeholder),
                    error = painterResource(id = R.drawable.error_placeholder),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Heritage Site Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                )
            }

            // Heritage Information
            item {
                HeritageInformation(heritage)
            }

        }

    }

}

@Composable
fun SaveToFavouriteFabButton(
    isFavourite: Boolean,
    onFabClicked: (Boolean) -> Unit
) {

    val mContext = LocalContext.current

    var isBookMarked by remember { mutableStateOf(isFavourite) }

    // this LaunchedEffect is used to update isBookMarked variable when isFavourite changes
    LaunchedEffect(isFavourite) {
        isBookMarked = isFavourite
    }

    FloatingActionButton(
        onClick = {
            onFabClicked(isBookMarked)
            isBookMarked = !isBookMarked
        }
    ) {
        Icon(
            imageVector = if (!isBookMarked) Icons.Default.BookmarkBorder else Icons.Default.Bookmark,
            contentDescription = "Save to Favourites Button"
        )
    }
}

@Composable
fun HeritageInformation(heritage: HeritageEntity) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column {

            // Tag Chip
            HeritageTagChip(heritage.type)

            Spacer(modifier = Modifier.height(8.dp))

            // Heritage Name
            Text(heritage.name, fontSize = 21.sp, fontWeight = FontWeight.Bold)

            // Heritage Country
            Text(Constants.convertCountryShortToFullText(heritage.target), fontSize = 15.sp)

            Spacer(Modifier.height(16.dp))

            // Basic Information
            HeritageDetailBasicInformation(heritage)

            Spacer(Modifier.height(16.dp))

            // Heritage Short Info
            Text("About", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            // To properly format the string before setting it
            Text(heritage.shortInfo.substringAfter("\n\n"))

            Spacer(Modifier.height(16.dp))

            if (!heritage.longInfo.isNullOrEmpty()) {
                // Heritage Long Info
                Text("About - Long Information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(heritage.longInfo)
            }
        }

    }
}

@Composable
fun HeritageDetailBasicInformation(heritage: HeritageEntity) {

    val mContext = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {

        // Section Headline
        Text("Basic Information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        // Latitude Longitude
        Text("Latitude: ${heritage.lat}")
        Text("Longitude: ${heritage.lng}")
        Text("Co-ordinates: ${heritage.coordinates}")

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            // Show Location in Map Button
            Button(
                onClick = { /*TODO("Move to Google Maps with Lat Long Data")*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.purple_500)
                )
            ) {
                Text("Show on Map")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // Opens Link in Browser Window
                    CustomTab.loadURL(mContext, heritage.page)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.purple_500)
                )
            ) {
                Text("Visit Link")
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Default.Link, contentDescription = null)
            }
        }
    }
}

@Composable
fun HeritageTagChip(heritageTag: String) {
    Text(
        text = heritageTag,
        color = Color.White,
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.purple_500),
                shape = CircleShape
            )
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
}
