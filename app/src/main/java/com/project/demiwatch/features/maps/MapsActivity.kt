package com.project.demiwatch.features.maps

import android.content.Intent
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
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.permissions.LocationPermissionHelper
import com.project.demiwatch.databinding.ActivityMapsBinding
import com.project.demiwatch.features.navigation.NavigationActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapsBinding
    private val mapViewModel: MapViewModel by viewModels()

    private lateinit var mapView: MapView
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private lateinit var patientCoordinate: Point

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

        binding.fabCenterLocation.setOnClickListener {
            val intentToNavigation = Intent(this, NavigationActivity::class.java)
            startActivity(intentToNavigation)
        }

        mapView = binding.mapView

        locationPermissionHelper = LocationPermissionHelper((WeakReference(this)))
        locationPermissionHelper.checkPermissions {
            mapViewModel.getLocationPatient().observe(this) { location ->
                when (location) {
                    is Resource.Error -> {
                        //show loading
                        Timber.tag("HomeFragment").e(location.message)
                    }
                    is Resource.Loading -> {
                        //show loading
                    }
                    is Resource.Message -> {
                        Timber.tag("HomeFragment").d(location.message)
                    }
                    is Resource.Success -> {
                        patientCoordinate = Point.fromLngLat(
                            location.data?.longitude ?: 0.0,
                            location.data?.latitude ?: 0.0
                        )
                        onMapReady()
                    }
                }
            }
        }

//        setupFabButton()
    }

    private fun setupBottomSheet() {
        YourPatientFragment().show(supportFragmentManager, YOUR_PATIENT_TAG)
    }

//    private fun setupFabButton() {
//        binding.fabCenterLocation.setOnClickListener {
//
//            val viewportPlugin = mapView.viewport
//            val overviewViewportState: OverviewViewportState =
//                viewportPlugin.makeOverviewViewportState(
//                    OverviewViewportStateOptions.Builder()
//                        .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0))
//                        .build()
//                )
//        }
//    }

    private fun addPatientLocation(style: Style) {
        val markerCoordinates = listOf(
            patientCoordinate,
        )

        val geoJsonString = FeatureCollection.fromFeatures(
            markerCoordinates.map {
                Feature.fromGeometry(it)
            }
        ).toJson()

        val source = GeoJsonSource.Builder("marker-source-id")
            .data(geoJsonString)
            .build()

        style.addSource(source)

        val vectorDrawable = ContextCompat.getDrawable(this, R.drawable.ic_location_on_24)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        style.addImage("marker-icon-id", bitmap)

        val layer = SymbolLayer("marker-layer-id", "marker-source-id")
        layer.iconImage("marker-icon-id")
        layer.iconSize(1.0)

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
        ) {
            initLocationUser()
            setupGesturesListener()
            addPatientLocation(it)
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationUser() {
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
        const val YOUR_PATIENT_TAG = "Your Patient Fragment"
    }
}