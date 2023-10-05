package com.example.touchbase.ui.screens.creationscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.touchbase.backend.*
import com.example.touchbase.ui.components.*
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun NewContactsScreen(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    Scaffold(
        topBar = { TitleBar("New Contact") },
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AddPhoto(
                    viewModel = viewmodel,
                    navController = navController,
                    id = viewmodel.newContactID.value
                )
                Text(text = "Add Photo", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
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
