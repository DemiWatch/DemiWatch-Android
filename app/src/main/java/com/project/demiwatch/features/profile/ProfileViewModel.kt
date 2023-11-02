package com.project.demiwatch.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private var userUseCase: UserUseCase,
): ViewModel() {

    fun deleteTokenUser() = viewModelScope.launch {
        userUseCase.deleteTokenUser()
    }
}