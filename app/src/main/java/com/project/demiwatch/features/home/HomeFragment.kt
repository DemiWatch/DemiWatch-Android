package com.project.demiwatch.features.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.permissions.LocationPermissionHelper
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.FragmentHomeBinding
import com.project.demiwatch.features.maps.MapsActivity
import com.project.demiwatch.features.navigation.NavigationActivity
import com.project.demiwatch.features.patient_detail.PatientDetailActivity
import timber.log.Timber
import java.lang.ref.WeakReference


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() =_binding!!
    private val homeViewModel:HomeViewModel by activityViewModels()

    private lateinit var mapView: MapView
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private lateinit var token: String
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
            val intentToMap = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intentToMap)
            return true
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPatientRouteCard()

        setupPatientListCard()

        setupPatientRoute()

        homeViewModel.getTokenUser().observe(this){
            token = it
        }

        mapView = binding.mapView

        locationPermissionHelper = LocationPermissionHelper((WeakReference(activity)))
        locationPermissionHelper.checkPermissions {
            homeViewModel.getLocationPatient().observe(this){location ->
                when(location){
                    is Resource.Error ->{
                        //show loading
                        Timber.tag("HomeFragment").e(location.message)
                    }
                    is Resource.Loading -> {
                        //show loading
                    }
                    is Resource.Message ->{
                        Timber.tag("HomeFragment").d(location.message)
                    }
                    is Resource.Success ->{
                        patientCoordinate = Point.fromLngLat(location.data?.longitude ?: 0.0, location.data?.latitude ?: 0.0)
                        onMapReady()
                    }
                }
            }
        }
    }


    private fun setupPatientListCard() {
        binding.cardPatientList.setOnClickListener {
            val intentToPatientDetail = Intent(requireContext(), PatientDetailActivity::class.java)
            startActivity(intentToPatientDetail)
        }
    }

    private fun setupPatientRouteCard() {
        binding.cardPatientRoute.setOnClickListener {
            val intentToPatientDetail = Intent(requireContext(), PatientDetailActivity::class.java)
            startActivity(intentToPatientDetail)
        }
    }

    private fun setupPatientRoute() {
        binding.btnPatientRoute.setOnClickListener {
            val intentToNavigation = Intent(requireContext(), NavigationActivity::class.java)
            startActivity(intentToNavigation)
        }
    }

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

        val vectorDrawable = ContextCompat.getDrawable( requireContext(), R.drawable.ic_location_on_24)
        val bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        style.addImage("marker-icon-id", bitmap)

        val layer = SymbolLayer("marker-layer-id", "marker-source-id")
        layer.iconImage("marker-icon-id")
        layer.iconSize(1.0)

        style.addLayer(layer)
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder().center(patientCoordinate).zoom(15.0).build()
        )

        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ){
//            initLocationUser()
            setupGesturesListener()
            addPatientLocation(it)
        }

        mapView.gestures.apply {
            doubleTapToZoomInEnabled = false
            doubleTouchToZoomOutEnabled = false
            increasePinchToZoomThresholdWhenRotating = false
            pinchScrollEnabled = false
            pinchToZoomEnabled = false
            pinchToZoomDecelerationEnabled = false
            increasePinchToZoomThresholdWhenRotating = false
            simultaneousRotateAndPinchToZoomEnabled = false
            scrollEnabled = false
            rotateEnabled = false
            quickZoomEnabled= false
        }

        mapView.getMapboxMap().addOnMapClickListener { point ->
            val intentToMap = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intentToMap)
            true
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

//    private fun initLocationUser() {
//        val locationComponentPlugin = mapView.location
//        locationComponentPlugin.updateSettings {
//            this.enabled = true
//            this.locationPuck = LocationPuck2D(
//                bearingImage = AppCompatResources.getDrawable(
//                    requireContext(),
//                    R.drawable.ic_location_inner_24,
//                ),
//                shadowImage = AppCompatResources.getDrawable(
//                    requireContext(),
//                    R.drawable.ic_location_outer_24,
//                ),
//                scaleExpression = Expression.interpolate {
//                    linear()
//                    zoom()
//                    stop {
//                        literal(0.0)
//                        literal(0.6)
//                    }
//                    stop {
//                        literal(20.0)
//                        literal(1.0)
//                    }
//                }.toJson()
//            )
//        }
//        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
//        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
//    }

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

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
}