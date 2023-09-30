package com.example.touchbase.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.touchbase.ui.components.AddButton
import com.example.touchbase.ui.components.ContactItem
import com.example.touchbase.ui.components.TitleBar
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun ContactsScreen(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    Scaffold(
        topBar = {TitleBar()},
        floatingActionButton = { AddButton(viewmodel = viewmodel)}
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ){
            items(viewmodel.touchBaseContacts) { item ->
                ContactItem(viewmodel, navController, item)
            }
        }
    }
}