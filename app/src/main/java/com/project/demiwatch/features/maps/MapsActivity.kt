package com.project.demiwatch.features.maps

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.project.demiwatch.R
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.project.demiwatch.core.utils.permissions.LocationPermissionHelper
import com.project.demiwatch.databinding.ActivityMapsBinding
import java.lang.ref.WeakReference


class MapsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapsBinding

    private lateinit var mapView: MapView
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        setupBackButton()

        mapView = binding.mapView

        locationPermissionHelper = LocationPermissionHelper((WeakReference(this)))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }

    private fun addPatientLocation(style: Style) {
        // Define your marker coordinates
        val markerCoordinates = listOf(
            Point.fromLngLat(-122.416, 37.784),
            Point.fromLngLat(-122.425, 37.784),
            // ... add as many markers as you want
        )

        // Convert FeatureCollection to its string representation
        val geoJsonString = FeatureCollection.fromFeatures(
            markerCoordinates.map {
                Feature.fromGeometry(it)
            }
        ).toJson()

        // Create a GeoJsonSource using the Builder
        val source = GeoJsonSource.Builder("marker-source-id")
            .data(geoJsonString)
            .build()

        // Add the source to the style
        style.addSource(source)

        val vectorDrawable = ContextCompat.getDrawable(this, R.drawable.ic_location_inner_24)
        val bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        // Add the marker icon to the style
        style.addImage("marker-icon-id", bitmap)

        // Create a SymbolLayer and set the iconImage to your marker icon
        val layer = SymbolLayer("marker-layer-id", "marker-source-id")
        layer.iconImage("marker-icon-id")
        layer.iconSize(1.0)

        // Add the SymbolLayer to the style
        style.addLayer(layer)
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun onMapReady() {
       mapView.getMapboxMap().setCamera(
           CameraOptions.Builder().zoom(14.0).build()
       )

        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ){
            initLocationComponent()
            setupGesturesListener()
            addPatientLocation(it)
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@MapsActivity,
                    R.drawable.ic_location_inner_24,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@MapsActivity,
                    R.drawable.ic_location_outer_24,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupActionBar(){
        supportActionBar?.hide()
    }
}