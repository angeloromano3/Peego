package com.example.peego.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

import com.example.peego.data.model.Bathroom
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

private val osmTileSource = XYTileSource(
    "OpenStreetMap DE",
    0,
    19,
    256,
    ".png",
    arrayOf("https://tile.openstreetmap.de/"),
    "© OpenStreetMap contributors"
)

@Composable
fun OsmMapView(
    bathrooms: List<Bathroom>,
    centerLat: Double = -23.5980,
    centerLng: Double = -46.6850,
    zoom: Double = 14.5,
    modifier: Modifier = Modifier,
    onMarkerClick: (Bathroom) -> Unit = {}
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier,
        factory = {
            MapView(context).apply {
                setTileSource(osmTileSource)
                setMultiTouchControls(true)
                controller.setZoom(zoom)
                controller.setCenter(GeoPoint(centerLat, centerLng))
            }
        },
        update = { mapView ->
            mapView.overlays.clear()
            bathrooms.forEach { bathroom ->
                val marker = Marker(mapView).apply {
                    position = GeoPoint(bathroom.latitude, bathroom.longitude)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = bathroom.name
                    setOnMarkerClickListener { _, _ ->
                        onMarkerClick(bathroom)
                        true
                    }
                }
                mapView.overlays.add(marker)
            }
            mapView.invalidate()
        }
    )
}
