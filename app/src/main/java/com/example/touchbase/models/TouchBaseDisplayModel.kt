package com.example.touchbase.models

import android.media.Image

data class TouchBaseDisplayModel(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var profile: Image
)
