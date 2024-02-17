package com.rajit.worldheritages.ui.components

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rajit.worldheritages.R

@Composable
fun HeritageDetailScreen() {

    val context = LocalContext.current.applicationContext

    Scaffold(
        topBar = { HeritageDetailTopAppBar() },
        floatingActionButton = { SaveToFavouriteFabButton() }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier.padding(contentPadding)
        ) {

            // Heritage Image
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("https://whc.unesco.org/uploads/thumbs/site_0246_0003-750-750-20140224103928.jpg")
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
                HeritageInformation()
            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeritageDetailTopAppBar() {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
            }
        },
        title = { Text("Heritage Details") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.purple_500),
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun SaveToFavouriteFabButton() {

    var isBookMarked by remember {
        mutableStateOf(false)
    }

    FloatingActionButton(onClick = { isBookMarked = !isBookMarked }) {
        Icon(
            imageVector = if (!isBookMarked) Icons.Default.BookmarkBorder else Icons.Default.Bookmark,
            contentDescription = "Save to Favourites Button"
        )
    }
}

@Composable
fun HeritageInformation() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column {

            // Tag Chip
            HeritageTagChip()

            Spacer(modifier = Modifier.height(8.dp))

            // Heritage Name
            Text("Sun Temple, Konârak", fontSize = 21.sp, fontWeight = FontWeight.Bold)

            // Heritage Country
            Text("Bharat (India)", fontSize = 15.sp)

            Spacer(Modifier.height(16.dp))

            // Basic Information
            HeritageDetailBasicInformation()

            Spacer(Modifier.height(16.dp))

            // Heritage Short Info
            Text("About", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("On the shores of the Bay of Bengal, bathed in the rays of the rising sun, the temple at Konarak is a monumental representation of the sun god Surya's chariot; its 24 wheels are decorated with symbolic designs and it is led by a team of six horses. Built in the 13th century, it is one of India's most famous Brahman sanctuaries.")

            Spacer(Modifier.height(16.dp))

            // Heritage Long Info
            Text("About - Long Information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                "Konârak is an outstanding testimony to the 13th-century kingdom of Orissa. It is directly and materially linked to Brahmin beliefs, and forms the invaluable link in the history of the diffusion of the cult of Surya, which originated in Kashmir during the 8th century and finally reached the shores of eastern India.\n\nOn the eastern coast of India, south of the Mahanadi Delta, is the Brahmin temple of Kimarak (still spelled as Konârak or Konârka), one of the most famous Brahmin sanctuaries of Asia. Konârak derives its name from Konârka, the presiding deity of the Sun Temple. Konârka is a combination of two words, kona (corner) and arka (Sun). It was one of the earliest centres of Sun worship in India. Built around 1250 in the reign of King Narasingha Deva (1238-64), it marks the apogee of the wave of foundations dedicated to the Sun God Surya; the entire temple was conceived as a chariot of the Sun God with a set of spokes and elaborate carvings.\n\nThe present Sun Temple was probably built by King Narashimhadev I (1238-64) of the Ganga dynasty to celebrate his victory over the Muslims. The temple fell into disuse in the early 17th century after it was desecrated by an envoy of the Mughal Emperor Jahangir. The legend has it that the temple was constructed by Samba, the son of Lord Krishna. Samba was afflicted by leprosy and after twelve years of penance he was cured by Surya, the Sun God, in whose honour he built this temple.\n\nAgainst the horizon, on the sandy shore, where the rising Sun emerges from the waters of the Gulf of Bengal, stands the temple, built from stone and carefully oriented so as to permit the first rays of the Sun to strike its principal entry. It is a monumental representation of the chariot of Surya pulled by a team of seven horses (six of which still exist and are placed on either side of the stairway leading to the sanctuary).\n\nOn the north and south sides, 24 wheels some 3 m in diameter, lavishly sculptured with symbolic motives referring to the cycle of the seasons and the months, complete the illusionary structure of the temple-chariot. Between the wheels, the plinth of the temple is entirely decorated with reliefs (fantastic lions, musicians and dancers, erotic groups). Like many Indian temples, Konârak comprises several distinct and well-organized spatial units. The vimana (principal sanctuary) was surmounted by a high tower with a sikkara which was razed in the 19th century; to the east, the jahamogana (audience hall) now dominates the ruins with its pyramidal mass, the original effect.\n\nFurther to the east, the natmandir (dance hall), today unroofed, rises on a high platform. Various subsidiary structures are still to be found within the enclosed area of the rectangular wall, which is punctuated by its gates and towers.\n\nApart from the Puranas, other religious texts also point towards the existence of a Sun temple at Konârak long before the present temple. Konârak was once a bustling port of Kalinga and had good maritime trade relations with South-East Asian countries."
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HeritageDetailBasicInformation() {

    Column(modifier = Modifier.fillMaxWidth()) {

        // Section Headline
        Text("Basic Information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        // Latitude Longitude
        Text("Latitude: 19.88751548484811")
        Text("Longitude: 86.09472")
        Text("Co-ordinates: N19 53 15 E86 5 40.992")

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            // Show Location in Map Button
            Button(
                onClick = { /*TODO("Move to Google Maps with Lat Long Data")*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.purple_500)
                )
            ){
                Text("Show on Map")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /*TODO("Implement Intent for opening links inside app")*/ },
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
fun HeritageTagChip() {
    Text(
        text = "Cultural",
        color = Color.White,
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.purple_500),
                shape = CircleShape
            )
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
}
