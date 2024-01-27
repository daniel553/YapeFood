package com.yape.food.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.yape.food.R
import com.yape.food.ui.MessageCardView
import com.yape.food.ui.theme.YapeFoodTheme

@Composable
fun MapViewScreen(state: MapState, onEvent: (MapEvent) -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        when (state) {
            MapState.Loading -> MapLoadingView()
            is MapState.Success -> MapSuccessView(lat = state.lat,
                lon = state.lon,
                onBackPressed = { onEvent(MapEvent.OnBackPressed) },
                onGoPressed = { onEvent(MapEvent.OnGoPressed(state.lat, state.lon)) })
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun MapSuccesViewPreview() {
    YapeFoodTheme {
        MapSuccessView(
            lat = 0.toDouble(),
            lon = 0.toDouble(),
            onBackPressed = {},
            onGoPressed = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MapSuccessView(
    lat: Double,
    lon: Double,
    onBackPressed: () -> Unit,
    onGoPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pos = LatLng(lat, lon)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pos, 9f)
    }
    //💡This is not a complex view, but constraint layout is used as an example
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (nav, maps, go) = createRefs()
        val startGuideline = createGuidelineFromTop(0.8f)

        //💡The map will constraint to parent
        GoogleMap(
            modifier = Modifier.constrainAs(maps) {
                top.linkTo(parent.top)  //💡Each constraint is linked to parent
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = pos)
            )
        }

        FilledIconButton(onClick = { onBackPressed() }, modifier = Modifier.constrainAs(nav) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "back")
        }


        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .padding(32.dp)
                .constrainAs(go) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 68.dp)  //💡Intentional to prevent cover the zoom buttons
                    top.linkTo(startGuideline)  //💡start guideline as %80 start
                    bottom.linkTo(parent.bottom, 16.dp)
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.map_title),
                    )
                    Text(
                        text = stringResource(id = R.string.map_subtitle),
                        fontSize = 10.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(0.8f)
                ) {
                    Button(onClick = { onGoPressed() }) {
                        Text(text = stringResource(id = R.string.more_details).uppercase())
                        Icon(
                            imageVector = Icons.Default.ArrowForward, contentDescription = null,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

            }
        }
    }
}


@Preview(heightDp = 400)
@Composable
fun MapLoadingViewPreview() {
    YapeFoodTheme {
        MapLoadingView(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun MapLoadingView(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        MessageCardView(
            title = stringResource(id = R.string.loading_title),
            subtitle = stringResource(id = R.string.loading_subtitle),
            background = painterResource(id = R.drawable.map),
            modifier = Modifier.fillMaxSize()
        )

        CircularProgressIndicator(
            modifier = Modifier.width(84.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

    }
}