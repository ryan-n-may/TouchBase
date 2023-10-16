package com.example.touchbase.ui.components

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.touchbase.Destination
import com.example.touchbase.backend.SimpleField
import com.example.touchbase.ui.dialogues.FYIDialogue
import com.example.touchbase.ui.dialogues.YesNoDialogue
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
    var show = remember{mutableStateOf(false)}
    YesNoDialogue(
        "Are you sure you want to delete this contact?",
        show,
        onYes = {
            viewModel.currentContactID.value = id
            viewModel.onEvent(TouchBaseEvent.DeleteContact)
            navController.navigate(Destination.ContactsScreen.route)
        })
    IconButton(onClick = {show.value = true},
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
        modifier = Modifier
            .padding(5.dp)
            .alpha(visibility.value)) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
    }
}

@Composable
fun SyncWithContactsButton(viewModel : TouchBaseViewModel){
    val activity = LocalContext.current as Activity
    var showDialog = remember{mutableStateOf(false)}
    FYIDialogue(
        content = "Syncing contacts with app. This may take a minute. ",
        showDialog,
        viewModel::refreshContactList
    )
    FloatingActionButton(
        modifier = Modifier.padding(5.dp),
        onClick = {
            viewModel.onEvent(TouchBaseEvent.CurrentActivity(activity))
            viewModel.onEvent(TouchBaseEvent.SyncContacts)
            showDialog.value = true
        }) {
        Icon(imageVector = Icons.TwoTone.Refresh, contentDescription = "Sync")
    }
}

@Composable
fun AddPhoto(viewModel : TouchBaseViewModel,
             navController: NavHostController,
             id : Int){
    IconButton(
        modifier = Modifier
            .padding(0.dp),
        onClick = {
            viewModel.currentContactID.value = id
            navController.navigate(Destination.CameraScreen.route)
    }) {
       Icon(
           modifier = Modifier
               .padding(0.dp)
               .clip(CircleShape)
               .background(Color.White),
           imageVector = Icons.Rounded.AddCircle,
           contentDescription = "Add photo"
       )
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
