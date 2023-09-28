package com.example.touchbase.models

import androidx.compose.ui.graphics.ImageBitmap

data class TouchBaseDisplayModel(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var profile: ImageBitmap?
)
