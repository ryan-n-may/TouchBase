package com.example.touchbase.viewmodel

import android.util.Log
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.touchbase.backend.*

const val TAG = "TouchBaseViewModel"

class TouchBaseViewModel(context: Context) : ViewModel() {
    private var db : CONTACT_DATABASE
    init {
        // Makes Database
        db = Room.databaseBuilder(
            context,
            CONTACT_DATABASE::class.java, "contact-db"
        ).allowMainThreadQueries().build()
    }

    fun testing(){
        Log.v(TAG, "Testing")
        /** Adding Contact to database **/
        Log.d(TAG, "Test: Adding person to database")
        val dd = DatabaseDriver(this.db)
        Log.d(TAG, "Clearning database before testing")
        dd.clearDatabases()
        if(dd.addNewContact("Ryan", "May", "", Relation.ImmediateFamily) &&
            dd.addNewContact("Chantelle", "Machado", "", Relation.ImmediateFamily)){
            Log.d(TAG,"Added new person to database")
        }
        if(!dd.addNewContact("Ryan", "May", "", Relation.ImmediateFamily)){
            Log.d(TAG,"Unique constraint to database was successful")
        }
        /** Listing contacts in database **/
        Log.d(TAG, "Test: Getting persons in database")
        dd.logContacts(TAG)
        /** Testing remove contact **/
        Log.d(TAG, "Test: Removing first person in database")
        val idOfFirstContact = dd.getContactList()[0].second
        dd.removeContact(idOfFirstContact)
        dd.logContacts(TAG)
        /** Adding field to database **/
        Log.d(TAG, "Test: adding a field to the database")
        val idOfSecondContact = dd.getContactList()[0].second
        val field = SimpleField(Titles.WorkPhone, "0479 105 458")
        dd.addNewContactField(idOfSecondContact, field)
        dd.logContactFields(TAG, idOfSecondContact)

    }


}