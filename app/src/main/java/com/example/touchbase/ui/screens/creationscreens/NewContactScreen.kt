package com.example.touchbase.ui.screens.creationscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.touchbase.backend.*
import com.example.touchbase.ui.components.*
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun NewContactsScreen(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    Scaffold(
        topBar = { TitleBar() },
        floatingActionButton = { 
            Row{ 
                BackButton(navController = navController)
                ConfirmButton(
                    navController = navController,
                    viewModel = viewmodel,
                    event = TouchBaseEvent.AddContact
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            AddPhoto(
                viewModel = viewmodel,
                navController = navController,
                id = viewmodel.newContactID.value
            )
            SimpleInput("First Name: ", content = {viewmodel.newContactFirstName.value}, onValueChange = {viewmodel.newContactFirstName.value = it})
            SimpleInput("Last Name: ", content = {viewmodel.newContactLastName.value}, onValueChange = {viewmodel.newContactLastName.value = it})
            RelationshipEnumSelector(
                Relation.values(),
                "Relationship:",
                viewmodel,
                viewmodel.newContactRelation
            )
        }
    }
}