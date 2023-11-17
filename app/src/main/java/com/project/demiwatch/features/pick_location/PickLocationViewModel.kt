package com.project.demiwatch.features.pick_location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapbox.geojson.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PickLocationViewModel @Inject constructor() : ViewModel() {
    private var _pickedLocationType = MutableLiveData<Int>()
    var pickedLocationType: LiveData<Int> = _pickedLocationType

    private var _pickedHomeLocation = MutableLiveData<Point>()
    var pickedHomeLocation: LiveData<Point> = _pickedHomeLocation

    private var _pickedDestinationLocation = MutableLiveData<Point>()
    var pickedDestinationLocation: LiveData<Point> = _pickedDestinationLocation

    fun setPickedLocationType(input: Int) {
        _pickedLocationType.value = input
    }

    fun setPickedHomeLocation(input: Point) {
        _pickedHomeLocation.value = input
    }

    fun setPickedDestinationLocation(input: Point) {
        _pickedDestinationLocation.value = input
    }


}