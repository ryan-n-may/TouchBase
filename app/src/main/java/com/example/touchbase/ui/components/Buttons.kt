package com.example.touchbase.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.touchbase.Destination
import com.example.touchbase.backend.SimpleField
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun AddContactButton(navController: NavHostController) {
    FloatingActionButton(onClick = {
        navController.navigate(Destination.NewContactScreen.route)
    },
        modifier = Modifier.padding(5.dp)) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "add")
    }
}

@Composable // also add view model evenet
fun ConfirmButton(navController: NavHostController,
                  viewModel: TouchBaseViewModel,
                  event: TouchBaseEvent
) {
    FloatingActionButton(onClick = {
        navController.popBackStack()
        viewModel.onEvent(event)
    },
        modifier = Modifier.padding(5.dp)) {
        Icon(imageVector = Icons.Default.Check, contentDescription = "add")
    }
}

@Composable
fun DeleteContactButton(navController: NavHostController, viewModel: TouchBaseViewModel, id: Int) {
    IconButton(onClick = {
        navController.navigate(Destination.ContactsScreen.route)
        viewModel.currentContactID.value = id
        viewModel.onEvent(TouchBaseEvent.DeleteContact)
    },
        modifier = Modifier.padding(0.dp)) {
        Icon(imageVector = Icons.Default.Close, contentDescription = "back")
    }
}

@Composable
fun DeleteFieldButton(navController : NavHostController, viewModel : TouchBaseViewModel, it : SimpleField){
    IconButton(
        onClick = {
            navController.navigate(Destination.ProfileScreen.route)
            viewModel.currentContactField = it
            viewModel.onEvent(TouchBaseEvent.DeleteContactField)
        },
        modifier = Modifier.padding(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Delete Field"
        )
    }
}


@Composable
fun BackButton(navController: NavHostController) {
    FloatingActionButton(onClick = { navController.popBackStack() },
        modifier = Modifier.padding(5.dp)) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
    }
}

@Composable
fun AddPhoto(){
    Row{
        Text(text = "Add Photo:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Add photo")
        }
    }
}
