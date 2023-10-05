package com.example.touchbase

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import android.Manifest
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.touchbase.ui.screens.ContactsScreen
import com.example.touchbase.ui.screens.creationscreens.NewContactsScreen
import com.example.touchbase.ui.screens.ProfileScreen
import com.example.touchbase.ui.screens.creationscreens.NewFieldScreen
import com.example.touchbase.ui.screens.NewCameraScreen
import com.example.touchbase.ui.screens.editscreens.EditContactScreen
import com.example.touchbase.ui.screens.editscreens.EditFieldScreen
import com.example.touchbase.ui.theme.TouchBaseTheme
import com.example.touchbase.viewmodel.TouchBaseViewModel

const val TAG = "MainActivity"

sealed class Destination(val route: String){
    object ContactsScreen: Destination("Contacts")
    object ProfileScreen: Destination("Profile")
    object NewContactScreen: Destination("NewContact")
    object EditContactScreen: Destination("EditContact")
    object AddFieldScreen: Destination("NewField")
    object EditFieldScreen: Destination("EditField")
    object CameraScreen: Destination("CameraScreen")
}

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i(TAG, "Permission granted")
        } else {
            Log.i(TAG, "Permission denied")
        }
    }

    @Suppress("UNCHECKED_CAST")
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
            TouchBaseTheme {
                val navController = rememberNavController()
                NavigationAppHost(navController, viewModel)
            }
        }

        requestCameraPermission()
    }

    private fun requestCameraPermission(){
        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                Log.d(TAG, "Permission to camera already granted.");
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) == true -> {
                Log.d(TAG, "Show camera permissions dialog");
            }

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        this.viewModel.cameraExecute.shutdown()
    }
}

@Composable
fun NavigationAppHost (navController: NavHostController, viewModel: TouchBaseViewModel) {
    NavHost(
        navController = navController,
        startDestination = Destination.ContactsScreen.route
    ){
        composable(route = Destination.ContactsScreen.route)    { ContactsScreen(navController,viewModel)       }
        composable(route = Destination.NewContactScreen.route)  { NewContactsScreen(navController,viewModel)    }
        composable(route = Destination.ProfileScreen.route)     { ProfileScreen(navController,viewModel)        }
        composable(route = Destination.AddFieldScreen.route)    { NewFieldScreen(navController, viewModel)      }
        composable(route = Destination.CameraScreen.route)      { NewCameraScreen(navController, viewModel)     }
        composable(route = Destination.EditContactScreen.route) { EditContactScreen(navController, viewModel)   }
        composable(route = Destination.EditFieldScreen.route)   { EditFieldScreen(navController, viewModel)     }
    }
}

