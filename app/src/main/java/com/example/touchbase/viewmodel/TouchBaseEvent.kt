package com.example.touchbase.viewmodel

import android.app.Activity

sealed class TouchBaseEvent{
    object AddContact: TouchBaseEvent()
    object UpdateContact: TouchBaseEvent()
    object AddContactField: TouchBaseEvent()
    object DeleteContactField: TouchBaseEvent()
    object DeleteContact: TouchBaseEvent()
    object AddPhoto : TouchBaseEvent()
    object EditContact : TouchBaseEvent()
    object EditField : TouchBaseEvent()
    object SyncContacts : TouchBaseEvent()
    data class ProfileSelected(val id: Int): TouchBaseEvent()
    data class CurrentActivity(val activity : Activity): TouchBaseEvent()
}