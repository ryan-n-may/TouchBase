package com.example.touchbase.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.touchbase.Destination
import com.example.touchbase.models.TouchBaseDisplayModel
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun ContactItem(
    viewModel: TouchBaseViewModel,
    navController: NavHostController,
    contact: TouchBaseDisplayModel,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray
            )
            .padding(10.dp)
            .clickable {
                viewModel.onEvent(TouchBaseEvent.ProfileSelected(contact.id))
                navController.navigate(Destination.ProfileScreen.route)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        RoundProfileImage(image = contact.profile, modifier = Modifier.height(100.dp))

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = contact.firstName, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text(text = contact.lastName, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RoundProfileImage(
    image: Bitmap,
    modifier: Modifier = Modifier
){
    Image(
        bitmap = image.asImageBitmap(),
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "TouchBase",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary,
            fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
            fontWeight = FontWeight.ExtraBold
        ) })
}

@Composable
fun AddButton(viewmodel: TouchBaseViewModel) {
    FloatingActionButton(onClick = { viewmodel.onEvent(TouchBaseEvent.AddContact) }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "add")
    }
}

@Composable
fun BottomProfileBar(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val padding = 16.dp
            val size = 20.sp
            Text(
                text = "Back",
                fontSize = size,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(start = padding, end = padding)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = "Update",
                fontSize = size,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(start = padding, end = padding)
                    .clickable { viewmodel.onEvent(TouchBaseEvent.UpdateContact) }
            )
            Text(
                text = "Delete",
                fontSize = size,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(start = padding, end = padding)
                    .clickable {
                        viewmodel.onEvent(TouchBaseEvent.DeleteContact)
                        navController.popBackStack()
                    }
            )
        }
    }
}

@Composable
fun ProfileField(label: String, content: () -> String, onValueChange: (String) -> Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "$label: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        TextField(value = content(), onValueChange = onValueChange, Modifier.fillMaxWidth())
    }
}

//@Composable
//fun Testing(viewModel : TouchBaseViewModel) {
//    Button(
//        onClick = {
////            viewModel.testing()
//        }
//    ) {
//        Text("Test")
//    }
//}