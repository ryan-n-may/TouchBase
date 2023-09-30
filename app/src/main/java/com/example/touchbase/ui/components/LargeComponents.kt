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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.touchbase.backend.Contact
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun ContactItem(
    viewModel: TouchBaseViewModel,
    navController: NavHostController,
    contact: Contact,
    modifier: Modifier = Modifier
) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                )
                .padding(10.dp)
                .clickable {
                    viewModel.onEvent(TouchBaseEvent.ProfileSelected(contact.id))
                    navController.navigate(Destination.ProfileScreen.route)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            RoundProfileImage(
                image = contact.image,
                modifier = Modifier
                    .height(100.dp)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 3.dp
                    )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(text = contact.firstName, fontSize = 25.sp, fontWeight = FontWeight.Medium)
                    Text(text = contact.lastName, fontSize = 25.sp, fontWeight = FontWeight.Medium)
                    Text(
                        text = contact.relation.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    DeleteContactButton(
                        navController = navController,
                        viewModel = viewModel,
                        id = contact.id
                    )
                }
            }
        }
}

@Composable
fun RoundProfileImage(
    image: Bitmap?,
    modifier: Modifier = Modifier
){
    if(image == null){
        Text("WARNING:\nPROFILE IMAGE\nNOT FOUND")
    } else {
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
fun BottomProfileBar(navController: NavHostController, viewmodel: TouchBaseViewModel) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val padding = 16.dp
            val size = 20.sp
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Icon",
                modifier = Modifier
                    .padding(start = padding, end = padding)
                    .clickable {
                        navController.navigate(Destination.ContactsScreen.route)
                    }
            )
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh Icon",
                modifier = Modifier
                    .padding(start = padding, end = padding)
                    .clickable {
                        viewmodel.onEvent(TouchBaseEvent.UpdateContact)
                        navController.navigate(Destination.ProfileScreen.route)
                    }
            )
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add Icon",
                modifier = Modifier
                    .padding(start = padding, end = padding)
                    .clickable {
                        navController.navigate(Destination.AddFieldScreen.route)
                    }
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon",
                modifier = Modifier
                    .padding(start = padding, end = padding)
                    .clickable {
                        viewmodel.onEvent(TouchBaseEvent.DeleteContact)
                        navController.navigate(Destination.ContactsScreen.route)
                    }
            )
        }
    }
}

@Composable
fun SimpleField(label: String, content: String){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            ) {
            Text(text = "$label: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = content, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SimpleInput(label: String, content: () -> String, onValueChange: (String) -> Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        TextField(value = content(), onValueChange = onValueChange, Modifier.fillMaxWidth())
    }
}



