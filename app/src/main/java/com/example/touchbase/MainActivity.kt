package com.example.touchbase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.touchbase.viewmodel.TouchBaseViewModel

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TouchBaseViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return TouchBaseViewModel(applicationContext) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Starting Main Activity onCreate")
        super.onCreate(savedInstanceState)
        setContent {
            Log.d(TAG, "Executing Testing(viewModel)")
            Testing(viewModel)
        }
    }
}

@Composable
fun Testing(viewModel : TouchBaseViewModel) {
    Button(
        onClick = {
            viewModel.testing()
        }
    ) {
        Text("Test")
    }
}

