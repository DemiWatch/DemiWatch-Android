package com.project.demiwatch.features.pick_location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PickLocationViewModel @Inject constructor(
    private val patientUseCase: PatientUseCase
) : ViewModel() {
    private var _pickedLocationType = MutableLiveData<Int>()
    var pickedLocationType: LiveData<Int> = _pickedLocationType

    private var _fromActivityType = MutableLiveData<Int>()
    var fromActivityType: LiveData<Int> = _fromActivityType

    private var _pickedHomeLocation = MutableLiveData<Point>()
    var pickedHomeLocation: LiveData<Point> = _pickedHomeLocation

    private var _pickedDestinationLocation = MutableLiveData<Point>()
    var pickedDestinationLocation: LiveData<Point> = _pickedDestinationLocation

    fun setPickedLocationType(input: Int) {
        _pickedLocationType.value = input
    }

    fun setFromActivityType(input: Int) {
        _fromActivityType.value = input
    }

    fun setPickedHomeLocation(input: Point) {
        _pickedHomeLocation.value = input
    }

    fun setPickedDestinationLocation(input: Point) {
        _pickedDestinationLocation.value = input
    }

    fun saveHomeLocationPatient(homeLocation: String) = viewModelScope.launch {
        patientUseCase.saveHomeLocationPatient(homeLocation)
    }

    fun saveDestinationLocationPatient(destinationLocation: String) = viewModelScope.launch {
        patientUseCase.saveDestinationLocationPatient(destinationLocation)
    }
}