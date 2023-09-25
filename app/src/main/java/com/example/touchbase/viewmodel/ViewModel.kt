package com.example.touchbase.viewmodel

import android.util.Log
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.touchbase.backend.*

const val TAG = "ViewModel"

class TouchBaseViewModel(context: Context) : ViewModel() {
    var db : CONTACT_DATABASE
    init {
        // Makes Database
        db = Room.databaseBuilder(
            context,
            CONTACT_DATABASE::class.java, "contact-database"
        ).allowMainThreadQueries().build()

    }

    fun testing(){
        Log.v(TAG, "Testing")
        // TESTING THE DATABASE DRIVER
        var dd = DatabaseDriver(this.db)
        dd.addNewContact("Ryan", "May", "", Relation.ImmediateFamily)
    }
}