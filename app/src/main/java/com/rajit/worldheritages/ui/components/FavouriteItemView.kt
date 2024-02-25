package com.rajit.worldheritages.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rajit.worldheritages.R
import com.rajit.worldheritages.data.model.FavouriteEntity
import com.rajit.worldheritages.util.Constants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FavouriteItemView(
    favouriteEntity: FavouriteEntity,
    onItemClicked: (FavouriteEntity) -> Unit,
    onItemLongClicked: (FavouriteEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onItemClicked(favouriteEntity)
                },
                onLongClick = {
                    onItemLongClicked(favouriteEntity)
                }
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column {

            Text(
                "Favourited",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        bottom = 4.dp
                    )
            )

            // Heritage Detail View
            Row(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp
                )
            ) {

                Column(Modifier.weight(1f)) {

                    Text(
                        text = favouriteEntity.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = favouriteEntity.shortInfo.substringAfter("\n\n"),
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(Modifier.width(10.dp))

                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                        .width(110.dp)
                        .aspectRatio(1 / 1f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(favouriteEntity.image)
                        .crossfade(enable = true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.error_placeholder), // Place Placeholder instead of error_placeholder
                    error = painterResource(id = R.drawable.error_placeholder),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "${favouriteEntity.name} Photo",
                )
            }

            // Heritage Type and Country Chips
            Row(
                modifier = Modifier
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
                    label = { Text(favouriteEntity.type, color = Color.White) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colorResource(R.color.purple_500)
                    ),
                    shape = CircleShape
                )

                Spacer(modifier = Modifier.width(10.dp))

                FilterChip(
                    selected = true,
                    onClick = { /*Do Nothing For Now*/ },
                    label = {
                        Text(
                            Constants.convertCountryShortToFullText(favouriteEntity.target),
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