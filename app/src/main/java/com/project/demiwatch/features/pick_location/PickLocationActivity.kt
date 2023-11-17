package com.project.demiwatch.features.pick_location

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.permissions.LocationPermissionHelper
import com.project.demiwatch.databinding.ActivityPickLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class PickLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPickLocationBinding
    private val pickLocationViewModel: PickLocationViewModel by viewModels()

    private lateinit var mapView: MapView
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private lateinit var pickedCoordinates: Point

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

        override fun onMoveEnd(detector: MoveGestureDetector) {

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPickLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        mapView = binding.mapView

        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }

        setupSaveButton()

        saveButtonIsClickable(false)
    }

    private fun saveButtonIsClickable(isClickable: Boolean) {
        binding.btnSave.isEnabled = isClickable
    }

    private fun setupSaveButton() {
//        binding.btnSave.setOnClickListener {
//            val intentToFillPatientProfile =
//                Intent(this@PickLocationActivity, FillProfilePatientActivity::class.java)
//
//            when (intent.getIntExtra(FLAG_SELECT_LOCATION, 0)) {
//                1 -> {
//                    pickLocationViewModel.setPickedHomeLocation(pickedCoordinates)
//                    intentToFillPatientProfile.putExtra(FLAG_SELECT_LOCATION, 1)
//                }
//                2 -> {
//                    pickLocationViewModel.setPickedHomeLocation(pickedCoordinates)
//                    intentToFillPatientProfile.putExtra(FLAG_SELECT_LOCATION, 2)
//                }
//                3 -> {
//                    pickLocationViewModel.setPickedDestinationLocation(pickedCoordinates)
//                    intentToFillPatientProfile.putExtra(FLAG_SELECT_LOCATION, 3)
//                }
//                4 -> {
//                    pickLocationViewModel.setPickedDestinationLocation(pickedCoordinates)
//                    intentToFillPatientProfile.putExtra(FLAG_SELECT_LOCATION, 4)
//                }
//                else -> {
//                    pickLocationViewModel.setPickedHomeLocation(pickedCoordinates)
//                    intentToFillPatientProfile.putExtra(FLAG_SELECT_LOCATION, 1)
//                }
//            }
//
//            startActivity(intentToFillPatientProfile)
//        }

        binding.btnSave.setOnClickListener {

        }
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )

        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }

        mapView.getMapboxMap().apply {
            addOnMapClickListener { point ->
                this.getStyle { style ->
                    pickedCoordinates = point
                    addMarkerLocation(point)

                    saveButtonIsClickable(true)
                }
                true
            }
        }
    }

    private fun addMarkerLocation(location: Point) {
        mapView.getMapboxMap().getStyle { style ->
            val markerCoordinate = Feature.fromGeometry(location)
            val source = style.getSourceAs<GeoJsonSource>(MARKER_SOURCE_ID)

            if (source == null) {
                val newSource = GeoJsonSource.Builder(MARKER_SOURCE_ID)
                    .data(FeatureCollection.fromFeatures(arrayOf(markerCoordinate)).toJson())
                    .build()

                style.addSource(newSource)
                addCustomMarkerImage()
                addMarkerLayer()
            } else {
                source.data(FeatureCollection.fromFeatures(arrayOf(markerCoordinate)).toJson())
            }
        }
    }

    private fun addCustomMarkerImage() {
        mapView.getMapboxMap().getStyle() { style ->
            val vectorDrawable = ContextCompat.getDrawable(this, R.drawable.ic_location_on_24)
            val bitmap = Bitmap.createBitmap(
                vectorDrawable!!.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            Canvas(bitmap).apply {
                vectorDrawable.setBounds(0, 0, width, height)
                vectorDrawable.draw(this)
            }

            style.addImage(MARKER_IMAGE_ID, bitmap)
        }
    }

    private fun addMarkerLayer() {
        mapView.getMapboxMap().getStyle { style ->
            if (style.getLayer(MARKER_LAYER_ID) == null) {
                val layer = SymbolLayer(MARKER_LAYER_ID, MARKER_SOURCE_ID)
                    .iconImage(MARKER_IMAGE_ID)
                    .iconSize(1.0)

                style.addLayer(layer)
            }
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
                    this@PickLocationActivity,
                    R.drawable.ic_location_inner_24,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@PickLocationActivity,
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

        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
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

        mapView.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }


    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }

    companion object {
        private const val MARKER_SOURCE_ID = "marker-source-id"
        private const val MARKER_IMAGE_ID = "marker-icon-id"
        private const val MARKER_LAYER_ID = "marker-layer-id"
    }
}