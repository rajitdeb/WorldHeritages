package com.rajit.worldheritages.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajit.worldheritages.util.Constants

@Composable
fun FilterByCountryItem(
    selectedCountry: String,
    onCountryClicked: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {

        Column {

            Text(text = "Filter by Country", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(12.dp))

            LazyHorizontalGrid(
                rows = GridCells.Fixed(1), // Number of Lines (vertically) Grid Takes
                modifier = Modifier.height(30.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(Constants.countryList.size) { position ->

                    val currentItem = Constants.countryList[position]

                    CountryChip(
                        selectedCountry,
                        country = currentItem,
                        chipTitle = Constants.convertCountryShortToFullText(currentItem)
                    ) { countrySelected ->
                        onCountryClicked(countrySelected)
                    }
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryChip(
    selectedCountry: String,
    country: String,
    chipTitle: String,
    onChipClicked: (String) -> Unit
) {

    FilterChip(
        selected = selectedCountry == country,
        onClick = {
            onChipClicked(country)
        },
        label = { Text(text = chipTitle) },
        shape = CircleShape
    )

}

@Composable
fun FilterByTagItem(
    selectedTag: String,
    onTagClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {

        Column {

            Text(text = "Filter by Tags", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(12.dp))

            LazyHorizontalGrid(
                rows = GridCells.Fixed(1), // Number of Lines (vertically) Grid Takes
                modifier = Modifier.height(30.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(Constants.tagList.size) { position ->

                    val currentItem = Constants.tagList[position]

                    TagChip(
                        selectedTag,
                        tag = currentItem,
                    ) { tagSelected ->
                        onTagClicked(tagSelected)
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagChip(
    selectedTag: String,
    tag: String,
    onTagClicked: (String) -> Unit
) {

    FilterChip(
        selected = selectedTag == tag,
        onClick = {
            onTagClicked(tag)
        },
        label = { Text(text = tag) },
        shape = CircleShape
    )
}

@Preview(showBackground = true)
@Composable
fun TagChipPreview() {
    TagChip(selectedTag = "All", tag = "Cultural") {
        
    }
}

@Preview(showBackground = true)
@Composable
fun FilterByTagItemPreview() {
    FilterByTagItem(selectedTag = "All") {

    }
}

@Preview(showBackground = true)
@Composable
fun CountryChipPreview() {
    CountryChip(selectedCountry = "ALL", country = "ALL", chipTitle = "All") {
        // Do Nothing
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetItemPreview() {
    FilterByCountryItem("ALL") {
        // Do Nothing
    }
}