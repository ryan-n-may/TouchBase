package com.example.touchbase.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.touchbase.Destination
import com.example.touchbase.backend.Contact
import com.example.touchbase.backend.SimpleField
import com.example.touchbase.viewmodel.TouchBaseEvent
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun ContactTitle(
    viewmodel : TouchBaseViewModel,
    navController : NavHostController
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "${viewmodel.currentContactFirstName.value} ${viewmodel.currentContactLastName.value}", fontSize = 25.sp, fontWeight = FontWeight.Medium)
        EditContactButton(viewmodel, navController, viewmodel.currentContactID.value)
    }
}

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
            horizontalArrangement = Arrangement.SpaceAround
        ){
            RoundProfileImage(
                viewModel = viewModel,
                navController = navController,
                image = contact.image,
                id = contact.id,
                modifier = Modifier
                    .height(100.dp)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 3.dp
                    )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "${contact.firstName} ${contact.lastName}",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = contact.relation.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
                Column() {
                    EditContactButton(
                        navController = navController,
                        viewModel = viewModel,
                        id = contact.id
                    )
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
    viewModel : TouchBaseViewModel,
    navController: NavHostController,
    image: Bitmap?,
    modifier: Modifier = Modifier,
    id : Int
){
    if(image == null){
        Text("WARNING:\nPROFILE IMAGE\nNOT FOUND")
    } else {
        BoxWithConstraints(
            modifier = Modifier.width(100.dp)
        ) {
            val boxWidth = this.maxWidth
            Box(
                modifier = Modifier.width(boxWidth)
            ) {
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
                        .padding(2.dp)
                        .clip(CircleShape)
                )
            }
            Box (
                modifier = Modifier.width(35.dp).padding(0.dp).offset((-10).dp, (-10).dp)
            ){
                AddPhoto(
                    viewModel = viewModel,
                    navController = navController,
                    id = id
                )
            }
        }
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
fun ShowSimpleField(navController : NavHostController,
                viewModel : TouchBaseViewModel,
                label: String,
                content: String,
                obj: SimpleField
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            Text(text = "$label: $content", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Row {
                EditFieldButton(navController, viewModel, obj)
                DeleteFieldButton(navController, viewModel, obj)
            }
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



