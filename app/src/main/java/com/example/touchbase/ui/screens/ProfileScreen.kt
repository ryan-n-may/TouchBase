package com.example.touchbase.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.touchbase.Destination
import com.example.touchbase.ui.components.BottomProfileBar
import com.example.touchbase.ui.components.DeleteFieldButton
import com.example.touchbase.ui.components.RoundProfileImage
import com.example.touchbase.ui.components.SimpleField
import com.example.touchbase.ui.components.TitleBar
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
            RoundProfileImage(image = viewmodel.profile, modifier = Modifier.height(150.dp))
            
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
                viewmodel.currentContactFieldList.forEach {
                    item {
                        Row{
                            DeleteFieldButton(navController, viewmodel, it)
                            SimpleField(label = it.Title.toString(), content = it.Content)
                        }
                    }
                }
            }
        }
    }
}