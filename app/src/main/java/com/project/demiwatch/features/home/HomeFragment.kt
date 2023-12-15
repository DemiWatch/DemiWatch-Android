package com.project.demiwatch.features.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import com.project.demiwatch.core.domain.model.PatientProfileCache
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.constants.PatientStatus
import com.project.demiwatch.core.utils.data_mapper.JsonMapper
import com.project.demiwatch.core.utils.permissions.LocationPermissionHelper
import com.project.demiwatch.core.utils.showToast
import com.project.demiwatch.databinding.FragmentHomeBinding
import com.project.demiwatch.features.maps.MapsActivity
import com.project.demiwatch.features.navigation.NavigationActivity
import com.project.demiwatch.features.patient_detail.PatientDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.ref.WeakReference

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var mapView: MapView
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private lateinit var token: String
    private lateinit var patientId: String
    private lateinit var patientCoordinate: Point
    private lateinit var userId: String

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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

        homeViewModel.getTokenUser().observe(this) {
            token = it
        }

        homeViewModel.getIdPatient().observe(this) {
            patientId = it

            setupPatientData(token, patientId)
        }

        homeViewModel.getIdUser().observe(this) {
            userId = it

            setupUserData(token, userId)
        }

        mapView = binding.mapView

        setupMap()

        setupCachedProfilePatient()
    }

    private fun setupCachedProfilePatient() {
        homeViewModel.getCachePatientProfile().observe(this) { patient ->
            if (patient != "" || !patient.isEmpty()) {
                val data = JsonMapper.convertToPatientProfile(patient)

                binding.apply {
                    cardPatientName.text = data.name
                    cardPatientSymptomps.text = data.patientSymptoms

                    cardPatientListName.text = data.name
                    cardPatientListSymptomps.text = data.patientSymptoms
                }
            }
        }
    }


    private fun startPeriodicRequests() {
        val periodicRequestFlow = createPeriodicRequestFlow()
        coroutineScope.launch {
            periodicRequestFlow.collect {
                fetchPatientLocation()
            }
        }
    }

    private fun createPeriodicRequestFlow() = flow {
        while (true) {
            emit(Unit)
            delay(15000)
        }
    }

    private fun setupPatientData(token: String, patientId: String) {
        homeViewModel.getPatient(token, patientId).observe(this) { patient ->
            when (patient) {
                is Resource.Error -> {
                    showLoading(false)
                    activity?.showToast("Pastikan internet anda terknoneksi dengan baik dan buka kembali aplikasi")
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Message -> {
                    Timber.tag("HomeFragment").d(patient.message)
                }
                is Resource.Success -> {
                    showLoading(false)

                    val cacheProfile = JsonMapper.convertPatientProfileToJson(
                        PatientProfileCache(
                            patient.data?.name!!,
                            patient.data.age.toString(),
                            patient.data.symptom,
                            patient.data.note,
                            patient.data.watchCode,
                            patient.data.homeName,
                            patient.data.destinationName,
                        )
                    )

                    homeViewModel.cachePatientProfile(cacheProfile)

                    binding.apply {
                        cardPatientName.text = patient.data?.name
                        cardPatientSymptomps.text = patient.data?.symptom

                        cardPatientListName.text = patient.data?.name
                        cardPatientListSymptomps.text = patient.data?.symptom
                    }
                }
            }
        }
    }

    private fun setupPatientStatus(status: String, isEmergency: Boolean) {
        binding.apply {
            if (!isEmergency) {
                when (status) {
                    "At Home" -> {
                        binding.cardPatientStatus.setStatus(PatientStatus.NOT_ACTIVE.status)
                        binding.cardPatientListStatus.setStatus(PatientStatus.NOT_ACTIVE.status)
                    }
                    "Arrived at destination." -> {
                        binding.cardPatientStatus.setStatus(PatientStatus.ARRIVED.status)
                        binding.cardPatientListStatus.setStatus(PatientStatus.ARRIVED.status)
                    }
                    "On the way to destination." -> {
                        binding.cardPatientStatus.setStatus(PatientStatus.ACTIVE.status)
                        binding.cardPatientListStatus.setStatus(PatientStatus.ACTIVE.status)
                    }
                }
            } else {
                binding.cardPatientStatus.setStatus(PatientStatus.DANGER.status)
                binding.cardPatientListStatus.setStatus(PatientStatus.DANGER.status)
            }
        }
    }

    private fun setupUserData(token: String, patientId: String) {
        homeViewModel.getUser(token, patientId).observe(this) { user ->
            when (user) {
                is Resource.Error -> {
                    showLoading(false)
                    activity?.showToast("Pastikan internet anda terknoneksi dengan baik dan buka kembali aplikasi")
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Message -> {
                    Timber.tag("HomeFragment").d(user.message)
                }
                is Resource.Success -> {
                    binding.tvHomeUsername.text = user.data?.name
                }
            }
        }
    }

    private fun setupMap() {
        locationPermissionHelper = LocationPermissionHelper((WeakReference(activity)))
        locationPermissionHelper.checkPermissions {
            startPeriodicRequests()
        }
    }

    private fun fetchPatientLocation() {
        homeViewModel.getLocationPatient().observe(this) { location ->
            when (location) {
                is Resource.Error -> {
                    Timber.tag("HomeFragment").e(location.message)
                }
                is Resource.Loading -> {

                }
                is Resource.Message -> {
                    Timber.tag("HomeFragment").d(location.message)
                }
                is Resource.Success -> {
                    setupPatientStatus(location.data?.message!!, location.data.emergency!!)

                    patientCoordinate = Point.fromLngLat(
                        location.data?.longitude ?: 0.0,
                        location.data?.latitude ?: 0.0
                    )

                    onMapReady()
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

    private fun addPatientLocation() {
        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
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

            val vectorDrawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_location_on_24)
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

//        val markerCoordinates = listOf(
//            patientCoordinate,
//        )
//
//        val geoJsonString = FeatureCollection.fromFeatures(
//            markerCoordinates.map {
//                Feature.fromGeometry(it)
//            }
//        ).toJson()
//
//        val source = GeoJsonSource.Builder("marker-source-id")
//            .data(geoJsonString)
//            .build()
//
//        style.addSource(source)
//
//        val vectorDrawable = ContextCompat.getDrawable( requireContext(), R.drawable.ic_location_on_24)
//        val bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
//        vectorDrawable.draw(canvas)
//
//        style.addImage("marker-icon-id", bitmap)
//
//        val layer = SymbolLayer("marker-layer-id", "marker-source-id")
//        layer.iconImage("marker-icon-id")
//        layer.iconSize(1.0)
//
//        style.addLayer(layer)
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder().center(patientCoordinate).zoom(13.0).build()
        )

        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationUser()
            setupGesturesListener()
            addPatientLocation()
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
            quickZoomEnabled = false
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

    private fun initLocationUser() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_location_inner_24,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_location_outer_24,
                ),
                scaleExpression = Expression.interpolate {
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
//        locationComponentPlugin.addOnIndicatorPositionChangedListener(
//            onIndicatorPositionChangedListener
//        )
//        locationComponentPlugin.addOnIndicatorBearingChangedListener(
//            onIndicatorBearingChangedListener
//        )
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}