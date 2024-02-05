package com.rajit.worldheritages.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rajit.worldheritages.R
import com.rajit.worldheritages.data.model.HeritageEntity

@Composable
fun HeritageItemView(
    modifier: Modifier = Modifier,
    heritage: HeritageEntity?
) {
    if (heritage != null) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier.weight(1f)) {
                    Text(text = heritage.name, modifier = modifier.padding(16.dp))
                    Text(
                        text = heritage.shortInfo.substringAfter("\n\n"),
                        maxLines = 7,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.padding(16.dp)
                    )
                }

                AsyncImage(
                    modifier = modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                        .width(150.dp)
                        .height(150.dp),
                    model = heritage.image,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.error_placeholder),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "${heritage.name} Photo",
                )
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

    HeritageItemView(heritage = heritageEntity)

}