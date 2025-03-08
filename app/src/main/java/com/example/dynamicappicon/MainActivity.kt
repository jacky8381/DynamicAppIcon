package com.example.dynamicappicon

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.dynamicappicon.presentation.MainScreen
import com.example.dynamicappicon.service.ChangeAppIconService
import com.example.dynamicappicon.ui.theme.DynamicAppIconTheme
import com.example.dynamicappicon.viewModel.AppIconConfig
import com.example.dynamicappicon.viewModel.ChangeAppIconViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("RememberReturnType")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         val changeAppIconViewModel: ChangeAppIconViewModel by viewModels()
        setContent {
            DynamicAppIconTheme {
                val context = LocalContext.current
                val uiState by changeAppIconViewModel.uiState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.outlineVariant,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                ),
                                title = {
                                    Text("Dynamic Icon")
                                }
                            )
                        }
                    ){innerPadding->
                        MainScreen(
                            modifier = Modifier .padding(innerPadding)
                        ) { value ->
                            changeAppIconViewModel.updateAppIcon(value)
                        }
                    }
                }
                LaunchedEffect(uiState.appIcon){
                    try {
                        if(uiState.appIcon.isNotEmpty()){
                            setNewIcon(uiState.appIcon,context)
                        }
                    }catch (e : Exception){
                        Log.d("Exception", e.stackTraceToString())
                    }
                }
            }
        }


    }
}

suspend fun setNewIcon(newAppIcon: String, context: Context) {
    coroutineScope {
        val campaignIconAlice = AppIconConfig.getAliasFromValue(newAppIcon)
        val serviceIntent = Intent(context, ChangeAppIconService::class.java)
        serviceIntent.putExtra("AppIconName", campaignIconAlice)
        ChangeAppIconService.appIcon = campaignIconAlice
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}