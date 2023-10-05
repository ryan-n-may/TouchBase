package com.example.touchbase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
            viewModel.currentContactField.value = it
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
fun EditFieldButton(navController : NavHostController, viewModel : TouchBaseViewModel, it : SimpleField){
    IconButton(
        onClick = {
            viewModel.currentContactField.value = it
            viewModel.currentFieldTitle.value = it.Title
            viewModel.currentFieldContents.value = it.Content
            navController.navigate(Destination.EditFieldScreen.route)
        },
        modifier = Modifier.padding(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Field"
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
fun ToggledBackButton(navController: NavHostController, visibility: MutableState<Float>) {
    FloatingActionButton(onClick = { navController.popBackStack() },
        modifier = Modifier.padding(5.dp).alpha(visibility.value)) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
    }
}

@Composable
fun AddPhoto(viewModel : TouchBaseViewModel,
             navController: NavHostController,
             id : Int){
    IconButton(
        onClick = {
            viewModel.currentContactID.value = id
            navController.navigate(Destination.CameraScreen.route)
    }) {
       Icon(imageVector = Icons.TwoTone.AddCircle, contentDescription = "Add photo")
    }
}

@Composable
fun EditContactButton(
    viewModel : TouchBaseViewModel,
    navController: NavHostController,
    id : Int){
    IconButton(
        onClick = {
            viewModel.currentContactID.value = id
            navController.navigate(Destination.EditContactScreen.route)
        }) {
        Icon(imageVector = Icons.TwoTone.Edit, contentDescription = "Edit Contact")
    }
}
