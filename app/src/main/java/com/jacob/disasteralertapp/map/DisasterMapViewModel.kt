package com.jacob.disasteralertapp.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacob.disasteralertapp.common.AuthData
import com.jacob.disasteralertapp.common.data.repositories.LocationRepository
import com.jacob.disasteralertapp.common.models.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class DisasterMapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val authData: AuthData
) : ViewModel() {
    private val isDisasterActive: Boolean
        get() = true

    private val _locationDataFlow: MutableStateFlow<List<LocationData>> = MutableStateFlow(
        emptyList()
    )

    /**
     * Returns a [StateFlow] of list of [LocationData] that can be observed if a disaster is active,
     * the list will be empty if there is no disaster active.
     */
    val locationDataFlow: StateFlow<List<LocationData>> = _locationDataFlow

    init {
        getLocationDetails()
    }

    private fun getLocationDetails() {
        if (!isDisasterActive) return

        Timber.e("loading data")
        viewModelScope.launch {
            locationRepository.getLocationForCity(authData.currentUser!!.city)
                .collect(_locationDataFlow::emit)
        }
    }
}
