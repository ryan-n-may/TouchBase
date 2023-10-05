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

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun NewFieldScreen(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    Scaffold(
        topBar = { TitleBar() },
        floatingActionButton = {
            Row{
                BackButton(navController = navController)
                ConfirmButton(
                    navController = navController,
                    viewModel = viewmodel,
                    event = TouchBaseEvent.AddContactField
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            TitleEnumSelector(
                Titles.values(),
                "Field Type:",
                viewmodel,
                viewmodel.newFieldTitle
            )
            SimpleInput("Content: ", content = {viewmodel.newFieldContents.value}, onValueChange = {viewmodel.newFieldContents.value = it})
        }
    }
}
