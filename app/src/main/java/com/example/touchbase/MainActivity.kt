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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.touchbase.ui.screens.ContactsScreen
import com.example.touchbase.ui.screens.NewContactsScreen
import com.example.touchbase.ui.screens.ProfileScreen
import com.example.touchbase.ui.screens.NewFieldScreen
import com.example.touchbase.ui.theme.TouchBaseTheme
import com.example.touchbase.viewmodel.TouchBaseViewModel

const val TAG = "MainActivity"

sealed class Destination(val route: String){
    object ContactsScreen: Destination("Contacts")
    object ProfileScreen: Destination("Profile")
    object NewContactScreen: Destination("NewContact")
    object AddFieldScreen: Destination("NewField")
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
    }
}

@Composable
fun NavigationAppHost (navController: NavHostController, viewModel: TouchBaseViewModel) {
    NavHost(
        navController = navController,
        startDestination = Destination.ContactsScreen.route
    ){
        composable(route = Destination.ContactsScreen.route)    { ContactsScreen(navController,viewModel) }
        composable(route = Destination.NewContactScreen.route)  { NewContactsScreen(navController,viewModel) }
        composable(route = Destination.ProfileScreen.route)     { ProfileScreen(navController,viewModel) }
        composable(route = Destination.AddFieldScreen.route)    { NewFieldScreen(navController, viewModel) }
    }
}

