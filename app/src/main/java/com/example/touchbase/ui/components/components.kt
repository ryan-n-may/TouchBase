package com.example.touchbase.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.touchbase.viewmodel.TouchBaseViewModel

@Composable
fun Testing(viewModel : TouchBaseViewModel) {
    Button(
        onClick = {
//            viewModel.testing()
        }
    ) {
        Text("Test")
    }
}