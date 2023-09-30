package com.example.touchbase.models

import android.graphics.Bitmap

data class TouchBaseDisplayModel(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var profile: Bitmap
)
