package com.example.touchbase.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.touchbase.ui.components.BottomProfileBar
import com.example.touchbase.ui.components.RoundProfileImage
import com.example.touchbase.ui.components.TitleBar
import com.example.touchbase.ui.components.profileField
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun ProfileScreen(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    Scaffold(
        topBar = { TitleBar() },
        bottomBar = { BottomProfileBar(navController = navController, viewmodel = viewmodel)}
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            if(viewmodel.profile == null)
            {
                RoundProfileImage(modifier = Modifier.height(200.dp))
            }else
            {
                RoundProfileImage(image = viewmodel.profile!!)
            }
            
            Text(text = viewmodel.id.toString())

            Button(
                onClick = {
                    viewmodel.onEvent(TouchBaseEvent.CameraTakePic)
                }
            ) {
                Text("Update Picture")
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)){
                item { profileField(label = "Relation: ", content = {viewmodel.relation}, onValueChange = {viewmodel.relation = it} )}
                item { profileField(label = "First Name: ", content = {viewmodel.firstName}, onValueChange = {viewmodel.firstName = it} )}
                item { profileField(label = "Last Name: ", content = {viewmodel.lastName}, onValueChange = {viewmodel.lastName = it} )}
                item { profileField(label = "Phone Number: ", content = {viewmodel.phoneNumber}, onValueChange = {viewmodel.phoneNumber = it} )}
                item { profileField(label = "Email: ", content = {viewmodel.email}, onValueChange = {viewmodel.email = it} )}
            }
        }
    }
}