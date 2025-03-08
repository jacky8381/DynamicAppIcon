package com.example.dynamicappicon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppIconUiState(
        var appIcon : String
 )


@HiltViewModel
class ChangeAppIconViewModel @Inject constructor() : ViewModel(){
    private val _uiState = MutableStateFlow(AppIconUiState(appIcon = ""))
    val uiState: StateFlow<AppIconUiState> = _uiState.asStateFlow()

    fun updateAppIcon(value : String){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
               it.copy(
                   appIcon = value
               )
           }
        }
    }
}