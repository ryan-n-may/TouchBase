package com.example.touchbase.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.touchbase.ui.components.BottomProfileBar
import com.example.touchbase.ui.components.RoundProfileImage
import com.example.touchbase.ui.components.TitleBar
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
            
            LazyColumn(modifier = Modifier.fillMaxSize().padding(3.dp)){
                item{
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(3.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "First Name: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        TextField(value = viewmodel.firstName, onValueChange = {viewmodel.firstName = it}, Modifier.fillMaxWidth())
                    }
                }
                item {
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(3.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Last Name: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        TextField(value = viewmodel.lastName, onValueChange = {viewmodel.lastName = it}, Modifier.fillMaxWidth())
                    }
                }
                item {
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(3.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Phone Number: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        TextField(value = viewmodel.phoneNumber, onValueChange = {viewmodel.phoneNumber = it}, Modifier.fillMaxWidth())
                    }
                }
                item {
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(3.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Email: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        TextField(value = viewmodel.email, onValueChange = {viewmodel.email = it}, Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}