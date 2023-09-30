package com.example.touchbase.viewmodel

sealed class TouchBaseEvent{
    object AddContact: TouchBaseEvent()
    object UpdateContact: TouchBaseEvent()
    object AddContactField: TouchBaseEvent()
    object DeleteContactField: TouchBaseEvent()
    object DeleteContact: TouchBaseEvent()
    object CameraOpen: TouchBaseEvent()
    object CameraTakePic: TouchBaseEvent()
    data class ProfileSelected(val id: Int): TouchBaseEvent()
}