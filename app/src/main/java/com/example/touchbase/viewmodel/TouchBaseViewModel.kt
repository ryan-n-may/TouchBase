package com.example.touchbase.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.touchbase.R
import com.example.touchbase.backend.CONTACT_DATABASE
import com.example.touchbase.models.TouchBaseDisplayModel

const val TAG = "TouchBaseViewModel"

class TouchBaseViewModel(context: Context) : ViewModel() {

    private var db : CONTACT_DATABASE
    var touchBaseContacts = mutableStateListOf<TouchBaseDisplayModel>()

    // Display Values
    var id by mutableIntStateOf(6595)
    var profile: Bitmap by mutableStateOf(BitmapFactory.decodeResource(context.resources, R.drawable.xavier))
    var relation by mutableStateOf("Brother")
    var firstName by mutableStateOf("Test")
    var lastName by mutableStateOf("McTest")
    var phoneNumber by mutableStateOf("95968751")
    var email by mutableStateOf("TestEmail@Curtin.edu.au")

    init {
        Log.v(TAG,"TouchBase Viewmodel Loaded")
        populateContactList(context)
        // Makes Database
        db = Room.databaseBuilder(
            context,
            CONTACT_DATABASE::class.java, "contact-db"
        ).allowMainThreadQueries().build()
    }

    fun onEvent(event: TouchBaseEvent){
        when(event){
            TouchBaseEvent.AddContact -> { Log.v(TAG,"Add Contact Event") }
            TouchBaseEvent.UpdateContact -> { Log.v(TAG,"Update Event") }
            TouchBaseEvent.DeleteContact -> { Log.v(TAG,"Delete Event") }
            TouchBaseEvent.CameraOpen -> { Log.v(TAG,"Camera Open Event") }
            TouchBaseEvent.CameraTakePic -> { Log.v(TAG,"Camera Take Pic Event") }
            is TouchBaseEvent.ProfileSelected -> { Log.v(TAG,"Profile Selected -> Id=${event.id}") }
        }
    }

    fun testing(){
        Log.v(TAG, "Testing")
        /** Adding Contact to database **/
        Log.d(TAG, "Test: Adding person to database")
//        val dd = DatabaseDriver(this.db)
//        Log.d(TAG, "Clearing database before testing")
//        dd.clearDatabases()
//        if(dd.addNewContact("Ryan", "May", "", Relation.ImmediateFamily) &&
//            dd.addNewContact("Chantelle", "Machado", "", Relation.ImmediateFamily)){
//            Log.d(TAG,"Added new person to database")
//        }
//        if(!dd.addNewContact("Ryan", "May", "", Relation.ImmediateFamily)){
//            Log.d(TAG,"Unique constraint to database was successful")
//        }
//        /** Listing contacts in database **/
//        Log.d(TAG, "Test: Getting persons in database")
//        dd.logContacts(TAG)
//        /** Testing remove contact **/
//        Log.d(TAG, "Test: Removing first person in database")
//        val idOfFirstContact = dd.getContactList()[0].second
//        dd.removeContact(idOfFirstContact)
//        dd.logContacts(TAG)
//        /** Adding field to database **/
//        Log.d(TAG, "Test: adding a field to the database")
//        val idOfSecondContact = dd.getContactList()[0].second
//        val field = SimpleField(Titles.WorkPhone, "0479 105 458")
//        dd.addNewContactField(idOfSecondContact, field)
//        dd.logContactFields(TAG, idOfSecondContact)
    }

    private fun populateContactList(context: Context){
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"Charles", "Xavier", BitmapFactory.decodeResource(context.resources, R.drawable.xavier)))
        touchBaseContacts.add(TouchBaseDisplayModel(9278,"Scott", "Summers", BitmapFactory.decodeResource(context.resources, R.drawable.scott)))
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"Henry", "Philip", BitmapFactory.decodeResource(context.resources, R.drawable.hank)))
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"Kurt", "Wagner", BitmapFactory.decodeResource(context.resources, R.drawable.kurt)))
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"James", "Logan", BitmapFactory.decodeResource(context.resources, R.drawable.logan)))
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"Ororo", "Munroe", BitmapFactory.decodeResource(context.resources, R.drawable.storm)))
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"Max", "Eisenhardt", BitmapFactory.decodeResource(context.resources, R.drawable.max)))
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"Anna Marie", "LeBeau", BitmapFactory.decodeResource(context.resources, R.drawable.anna)))
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"Remy Etienne", "LeBeau", BitmapFactory.decodeResource(context.resources, R.drawable.remy)))
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"Wade Winston", "Wilson", BitmapFactory.decodeResource(context.resources, R.drawable.wade)))
    }
}