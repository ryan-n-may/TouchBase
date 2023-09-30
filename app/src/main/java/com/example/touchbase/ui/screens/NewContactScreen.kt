package com.example.touchbase.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.touchbase.backend.*
import com.example.touchbase.ui.components.*
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel

@OptIn(ExperimentalStdlibApi::class)
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
            AddPhoto()
            SimpleInput("First Name: ", content = {viewmodel.newContactFirstName}, onValueChange = {viewmodel.newContactFirstName = it})
            SimpleInput("Last Name: ", content = {viewmodel.newContactLastName}, onValueChange = {viewmodel.newContactLastName = it})
            RelationshipEnumSelector(
                Relation.values(),
                "Relationship:",
                viewmodel
            )
        }
    }
}
