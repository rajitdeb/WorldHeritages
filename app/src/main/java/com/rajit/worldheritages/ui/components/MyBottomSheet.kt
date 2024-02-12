package com.rajit.worldheritages.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rajit.worldheritages.R
import com.rajit.worldheritages.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheet(
    onSubmit: (Pair<String, String>) -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit
) {

    val mAppContext = LocalContext.current.applicationContext
    val mSheetState = rememberModalBottomSheetState()
    var mSelectedCountry by remember { mutableStateOf(Constants.DEFAULT_COUNTRY_FILTER) }
    var mSelectedTag by remember { mutableStateOf(Constants.DEFAULT_TAG_FILTER) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = mSheetState,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 56.dp
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Filter by Country
            FilterByCountryItem(selectedCountry = mSelectedCountry) { selectedCountry ->
                mSelectedCountry = selectedCountry
            }

            // Filter By Tag
            FilterByTagItem(selectedTag = mSelectedTag) { selectedTag ->
                mSelectedTag = selectedTag
            }

            // Submit Button
            ElevatedButton(
                onClick = {
                    onSubmit(Pair(mSelectedCountry, mSelectedTag))
                    onDismiss()
                    Toast.makeText(
                        mAppContext,
                        "Country: $mSelectedCountry, Tag: $mSelectedTag",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorResource(R.color.purple_500),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Submit".uppercase())
            }

            // Reset Filters
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        onReset()
                        onDismiss()
                    },
                ) {
                    Text("Reset Filters")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomSheetPreview() {
    MyBottomSheet(
        onSubmit = {},
        onReset = {},
        onDismiss = {}
    )
}