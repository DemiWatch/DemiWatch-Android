package com.project.demiwatch.features.pick_location

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
import com.google.gson.Gson
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
import com.project.demiwatch.databinding.FragmentPickLocationBinding
import com.project.demiwatch.features.fill_profile.patient.FillProfilePatientActivity
import com.project.demiwatch.features.patient_detail.change_address.ChangeAddressActivity
import java.lang.ref.WeakReference

class PickLocationFragment : Fragment() {
    private var _binding: FragmentPickLocationBinding? = null
    private val binding get() = _binding!!
    private val pickLocationViewModel: PickLocationViewModel by activityViewModels()

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

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = binding.mapView
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }

        setupSaveButton()

        saveButtonIsClickable(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun saveButtonIsClickable(isClickable: Boolean) {
        binding.btnSave.isEnabled = isClickable
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            pickLocationViewModel.pickedLocationType.observe(this) { type ->
                val location = Gson().toJson(pickedCoordinates)
                when (type) {
                    1 -> {
                        pickLocationViewModel.saveHomeLocationPatient(location)
                    }
                    2 -> {
                        pickLocationViewModel.saveHomeLocationPatient(location)
                    }
                    3 -> {
                        pickLocationViewModel.saveDestinationLocationPatient(location)
                    }
                    4 -> {
                        pickLocationViewModel.saveDestinationLocationPatient(location)
                    }
                }
            }

            pickLocationViewModel.fromActivityType.observe(this) { type ->
                val intentBack = when (type) {
                    1 -> {
                        Intent(requireActivity(), FillProfilePatientActivity::class.java)
                    }
                    2 -> {
                        Intent(requireActivity(), ChangeAddressActivity::class.java)
                    }
                    else -> {
                        Intent(requireActivity(), ChangeAddressActivity::class.java)
                    }
                }
                startActivity(intentBack)
            }
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
                    addMarkerLocation(style, point)
                    saveButtonIsClickable(true)
                }
                true
            }
        }
    }

    private fun addMarkerLocation(style: Style, location: Point) {
        val markerCoordinate = Feature.fromGeometry(location)

        val source = style.getSourceAs<GeoJsonSource>(MARKER_SOURCE_ID)
        if (source == null) {
            val newSource = GeoJsonSource.Builder(MARKER_SOURCE_ID)
                .data(FeatureCollection.fromFeatures(arrayOf(markerCoordinate)).toJson())
                .build()

            style.addSource(newSource)
            addCustomMarkerImage(style)
            addMarkerLayer(style)
        } else {
            // Source exists, so just update its data
            source.data(FeatureCollection.fromFeatures(arrayOf(markerCoordinate)).toJson())
        }
    }

    private fun addCustomMarkerImage(style: Style) {
        // Add a custom marker icon (similar to your existing bitmap creation code)
        val vectorDrawable =
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_location_on_24)
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

    private fun addMarkerLayer(style: Style) {
        // Add the layer only if it doesn't exist
        if (style.getLayer(MARKER_LAYER_ID) == null) {
            val layer = SymbolLayer(
                MARKER_LAYER_ID,
                MARKER_SOURCE_ID
            )
                .iconImage(MARKER_IMAGE_ID)
                .iconSize(1.0)

            style.addLayer(layer)
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

    companion object {
        private const val MARKER_SOURCE_ID = "marker-source-id"
        private const val MARKER_IMAGE_ID = "marker-icon-id"
        private const val MARKER_LAYER_ID = "marker-layer-id"

        const val HOME_LOCATION_CODE = "HOME_LOCATION"
        const val DESTINATION_LOCATION_CODE = "DESTINATION_LOCATION"

        const val FLAG_SELECT_LOCATION = "FLAG_SELECT_LOCATION"
    }
}