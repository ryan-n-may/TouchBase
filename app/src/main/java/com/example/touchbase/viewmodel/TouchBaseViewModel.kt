package com.example.touchbase.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.touchbase.backend.CONTACT_DATABASE
import com.example.touchbase.models.TouchBaseDisplayModel

const val TAG = "TouchBaseViewModel"

class TouchBaseViewModel(context: Context) : ViewModel() {

    private var db : CONTACT_DATABASE
    var touchBaseContacts = mutableStateListOf<TouchBaseDisplayModel>()

    // Display Values
    var id by mutableIntStateOf(6595)
    var profile: ImageBitmap? by mutableStateOf(null)
    var relation by mutableStateOf("Brother")
    var firstName by mutableStateOf("Test")
    var lastName by mutableStateOf("McTesterson")
    var phoneNumber by mutableStateOf("95968751")
    var email by mutableStateOf("TestEmail@Curtin.edu.au")

    init {
        Log.v(TAG,"TouchBase Viewmodel Loaded")
        populateContactList()
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
//        Log.d(TAG, "Clearning database before testing")
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

    private fun populateContactList(){
        touchBaseContacts.add(TouchBaseDisplayModel(3452,"John", "Doe", null))
        touchBaseContacts.add(TouchBaseDisplayModel(7343,"Ryan", "May", null))
        touchBaseContacts.add(TouchBaseDisplayModel(7654,"Keven", "Rashleigh", null))
        touchBaseContacts.add(TouchBaseDisplayModel(1298,"Sajib", "Mistry", null))
        touchBaseContacts.add(TouchBaseDisplayModel(6658,"Bruce", "Wayne", null))
        touchBaseContacts.add(TouchBaseDisplayModel(2853,"Peter", "Parker", null))
        touchBaseContacts.add(TouchBaseDisplayModel(9723,"Ash", "Katchum", null))
        touchBaseContacts.add(TouchBaseDisplayModel(2387,"Sonic", "Hedgehog", null))
        touchBaseContacts.add(TouchBaseDisplayModel(4578,"Luigi", "Mario", null))
    }
}