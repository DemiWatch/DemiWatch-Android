package com.project.demiwatch.features.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.demiwatch.R
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.project.demiwatch.databinding.ActivityMapsBinding

var mapView: MapView? = null

class MapsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        mapView = binding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
    }

    private fun setupActionBar(){
        supportActionBar?.hide()
    }
}