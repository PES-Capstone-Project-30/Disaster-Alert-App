package com.jacob.disasteralertapp.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacob.disasteralertapp.common.AuthData
import com.jacob.disasteralertapp.common.data.repositories.DisasterDataRepository
import com.jacob.disasteralertapp.common.data.repositories.UserLocationRepository
import com.jacob.disasteralertapp.common.models.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel
class DisasterMapViewModel @Inject constructor(
    private val authData: AuthData,
    private val userLocationRepository: UserLocationRepository,
    private val disasterDataRepository: DisasterDataRepository
) : ViewModel() {
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

    private fun getLocationDetails() = viewModelScope.launch {
        val isDisasterActive =
            disasterDataRepository.isDisasterActive(authData.currentUser!!.city)
                .firstOrNull() ?: false
        if (!isDisasterActive) return@launch

        userLocationRepository.getLocationForCity(authData.currentUser!!.city)
            .collect(_locationDataFlow::emit)
    }
}
