package com.example.touchbase.ui.screens.editscreens

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
fun EditFieldScreen(navController: NavHostController, viewmodel: TouchBaseViewModel) {
        Scaffold(
                topBar = { TitleBar("Edit Field") },
                floatingActionButton = {
                        Row{
                                BackButton(navController = navController)
                                ConfirmButton(
                                        navController = navController,
                                        viewModel = viewmodel,
                                        event = TouchBaseEvent.EditField
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
                                viewmodel.currentFieldTitle
                        )
                        SimpleInput("Content: ", content = {viewmodel.currentFieldContents.value}, onValueChange = {viewmodel.currentFieldContents.value = it})
                }
        }
}
