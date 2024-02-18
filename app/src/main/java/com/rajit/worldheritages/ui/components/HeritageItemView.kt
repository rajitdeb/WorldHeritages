package com.rajit.worldheritages.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rajit.worldheritages.R
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeritageItemView(
    modifier: Modifier = Modifier,
    heritage: HeritageEntity?,
    onListItemClicked: (HeritageEntity) -> Unit
) {
    if (heritage != null) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onListItemClicked(heritage) },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column {

                // Heritage Detail View
                Row(
                    modifier = modifier.padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    Column(modifier.weight(1f)) {

                        Text(
                            text = heritage.name,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = heritage.shortInfo.substringAfter("\n\n"),
                            fontSize = 13.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier.width(10.dp))

                    AsyncImage(
                        modifier = modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Black)
                            .width(110.dp)
                            .aspectRatio(1 / 1f),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(heritage.image)
                            .crossfade(enable = true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.error_placeholder), // Place Placeholder instead of error_placeholder
                        error = painterResource(id = R.drawable.error_placeholder),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "${heritage.name} Photo",
                    )
                }

                // Heritage Type and Country Chips
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 10.dp
                        )
                ) {

                    FilterChip(
                        selected = true,
                        onClick = { /*Do Nothing For Now*/ },
                        label = { Text(heritage.type, color = Color.White) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorResource(R.color.purple_500)
                        ),
                        shape = CircleShape
                    )

                    Spacer(modifier = modifier.width(10.dp))

                    FilterChip(
                        selected = true,
                        onClick = { /*Do Nothing For Now*/ },
                        label = {
                            Text(
                                Constants.convertCountryShortToFullText(heritage.target),
                                color = Color.White
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorResource(R.color.purple_500)
                        ),
                        shape = CircleShape
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun HeritageItemViewPreview(
    heritageEntity: HeritageEntity = HeritageEntity(
        id = 4,
        year = 1978,
        target = "DEU",
        name = "Aachen Cathedral 2",
        type = "Cultural",
        region = "EUR",
        regionLong = "Europe and NorthAmerica",
        coordinates = "N50 40 28 E6 5 4",
        lat = 50.77444444444444,
        lng = 6.084444444444444,
        page = "http://whc.unesco.org/en/list/3",
        image = "https://whc.unesco.org/uploads/thumbs/site_0003_0001-750-750-20151104122109.jpg",
        imageAuthor = "Aachen Cathedral © Mario Santana",
        shortInfo = "Aachen Cathedral \\n\\nConstruction of this palatine chapel, with its octagonal basilica and cupola, began c. 790–800 under the Emperor Charlemagne.",
        longInfo = "With"
    )
) {

    HeritageItemView(heritage = heritageEntity) {

    }

}