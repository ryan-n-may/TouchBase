package com.example.touchbase.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.touchbase.backend.Relation
import com.example.touchbase.backend.Titles
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun RelationshipEnumSelector(options : Array<Relation>, title : String, viewModel : TouchBaseViewModel){
    var expanded by remember { mutableStateOf(false) }
    Box{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            IconButton(
                onClick = {
                    expanded = true
                }) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Enum Selector")
            }
            Text(viewModel.newContactRelation.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            // adding items
            options.forEach{
                DropdownMenuItem(
                    text = {
                        Text(text = it.toString())
                    },
                    onClick = {
                        viewModel.newContactRelation = it
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TitleEnumSelector(options : Array<Titles>, title : String, viewModel : TouchBaseViewModel){
    var expanded by remember { mutableStateOf(false) }
    Box{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            IconButton(
                onClick = {
                    expanded = true
                }) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Enum Selector")
            }
            Text(viewModel.newFieldTitle.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            // adding items
            options.forEach{
                DropdownMenuItem(
                    text = {
                        Text(text = it.toString())
                    },
                    onClick = {
                        viewModel.newFieldTitle = it
                        expanded = false
                    }
                )
            }
        }
    }
}