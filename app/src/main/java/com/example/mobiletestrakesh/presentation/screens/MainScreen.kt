package com.example.mobiletestrakesh.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mobiletestrakesh.R
import com.example.mobiletestrakesh.domain.model.RidesItem
import com.example.mobiletestrakesh.other.Constants
import com.example.mobiletestrakesh.presentation.RidesFilter
import com.example.mobiletestrakesh.presentation.RidesListsEvent
import com.example.mobiletestrakesh.presentation.RidesViewModel
import com.example.mobiletestrakesh.ui.theme.*

@Composable
fun MainScreen(ridesViewModel: RidesViewModel = hiltViewModel()) {

    val state = ridesViewModel.state

    Surface() {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(BlackHeadingBg)
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Edvora",
                        style = MaterialTheme.typography.h1
                    )
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Text(
                            text = state.user.name,
                            style = MaterialTheme.typography.h2
                        )

                        AsyncImage(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape),
                            model = state.user.url, contentDescription = "user image"
                        )
                    }
                }
            }
        ) {

            var showFilterDialog by rememberSaveable { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Text(
                            text = "Rides:",
                            style = MaterialTheme.typography.h5,
                            color = LightLabel
                        )


                        TopLabelTextButtons(
                            name = "Nearest",
                            selected = state.selectedRidesFilter == RidesFilter.NEAREST,
                            selectionDone = {
                                ridesViewModel
                                    .onEvent(RidesListsEvent.SelectedRideFilter(RidesFilter.NEAREST))
                            }
                        )
                        TopLabelTextButtons(name = "Upcoming ",
                            selected = state.selectedRidesFilter == RidesFilter.UPCOMING,
                            selectionDone = {
                                ridesViewModel
                                    .onEvent(RidesListsEvent.SelectedRideFilter(RidesFilter.UPCOMING))
                            }
                        )
                        TopLabelTextButtons(name = "Past ",
                            selected = state.selectedRidesFilter == RidesFilter.PAST,
                            selectionDone = {
                                ridesViewModel
                                    .onEvent(RidesListsEvent.SelectedRideFilter(RidesFilter.PAST))
                            }
                        )


                    }


                    Button(
                        onClick = { showFilterDialog = !showFilterDialog },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)

//                        modifier = Modifier
//                            .wrapContentSize()
//                            .background(MaterialTheme.colors.background)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Filter",
                            modifier = Modifier.size(width = 12.dp, height = 8.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Filters",
                            style = MaterialTheme.typography.body2,
                            color = FiltersTextColor,
                            maxLines = 1
                        )
                    }

                }

                AllRides(state.ridesList)

            }

            if (showFilterDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.Black.copy(0.5f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.4f)
                            .fillMaxWidth()
                            .padding(end = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .wrapContentHeight()
                                .background(
                                    color = DropDownColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .align(Alignment.BottomEnd)
                        ) {
                            /**FilterDialog(
                            allStateList = state.allStateFilter,
                            allCityList = state.allCityFilter,
                            stateCityList = state.stateCityFilter,
                            selectedStateCity = state.selectedStateCity,

                            /** Tells the view model to filter
                             * the list acc to given state */
                            filterSelectedStateCity = { currentStateCity->

                            ridesViewModel.onEvent(RidesListsEvent.FilterStateCity(
                            selectedStateCity = currentStateCity,
                            ))
                            },
                            onDismiss = {
                            showFilterDialog = false
                            }
                            )*/


                            DemoFilterDialog(
                                allStateList = state.allStateFilter,
                                allCityList = state.allCityFilter,
                                stateCityList = state.stateCityFilter,
//                                selectedStateCity = state.selectedStateCity,
                                onDismiss = { showFilterDialog = false }
                            )

                            /**
                            Filters(
                            allStateList = state.allStateFilter,
                            allCityList = state.allCityFilter,
                            stateCityList = state.stateCityFilter
                            )*/

                        }


                    }
                }
            }

        }

    }
}


@Composable
fun TopLabelTextButtons(name: String, selected: Boolean, selectionDone: () -> Unit) {

    TextButton(
        onClick = { selectionDone() },
    ) {
        Text(
            text = name,
            style = if (selected)
                MaterialTheme.typography.h3
            else
                MaterialTheme.typography.h4,
            color = if (selected)
                MaterialTheme.colors.onSecondary
            else
                UnselectedWhiteTextColor

        )
    }

}

@Composable
fun AllRides(ridesList: List<RidesItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(ridesList.size) { i ->
            SingleRideCard(ridesList[i])
        }
    }
}


@Preview
@Composable
fun PreviewMainScreen() {
    MobileTestRakeshTheme {
        MainScreen()
    }
}