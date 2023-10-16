package com.example.touchbase.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.touchbase.ui.components.AddContactButton
import com.example.touchbase.ui.components.ContactItem
import com.example.touchbase.ui.components.SyncWithContactsButton
import com.example.touchbase.ui.components.TitleBar
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun ContactsScreen(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    Scaffold(
        topBar = {TitleBar("TOUCHBASE")},
        floatingActionButton = { Column{
            AddContactButton(navController = navController)
            SyncWithContactsButton(viewModel = viewmodel)
        }}

    ) { innerPadding ->
        Column {
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(viewmodel.touchBaseContactList) { item ->
                    ContactItem(viewmodel, navController, item)
                }
            }
            Text(text = viewmodel.test.value)
        }
    }
}