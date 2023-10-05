package com.example.touchbase.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.touchbase.ui.components.*
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun ProfileScreen(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    Scaffold(
        topBar = { TitleBar("Profile") },
        bottomBar = { BottomProfileBar(navController = navController, viewmodel = viewmodel)}
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            RoundProfileImage(
                viewModel = viewmodel,
                navController = navController,
                image = viewmodel.currentContactImage.value,
                id = viewmodel.currentContactID.value,
                modifier = Modifier.height(150.dp))
            ContactTitle(viewmodel = viewmodel, navController = navController)
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)){
                viewmodel.currentContactFieldList.forEach {
                    item {
                        Row{
                            ShowSimpleField(
                                navController = navController,
                                viewModel = viewmodel,
                                label = it.Title.toString(),
                                content = it.Content,
                                obj = it)
                        }
                    }
                }
            }
        }
    }
}