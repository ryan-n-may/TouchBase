package com.example.touchbase.viewmodel

sealed class TouchBaseEvent{
    object AddContact: TouchBaseEvent()
    object UpdateContact: TouchBaseEvent()
    object AddContactField: TouchBaseEvent()
    object DeleteContactField: TouchBaseEvent()
    object DeleteContact: TouchBaseEvent()
    object AddPhoto : TouchBaseEvent()
    object EditContact : TouchBaseEvent()
    object EditField : TouchBaseEvent()
    data class ProfileSelected(val id: Int): TouchBaseEvent()
}