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
import com.example.touchbase.backend.*

const val TAG = "TouchBaseViewModel"

class TouchBaseViewModel(context: Context) : ViewModel() {

    private var db : CONTACT_DATABASE
    private var dao : CONTACT_DAO
    private lateinit var dd : DatabaseDriver

    // Display Values
    var id by mutableIntStateOf(6595)
    var profile: Bitmap by mutableStateOf(BitmapFactory.decodeResource(context.resources, R.drawable.xavier))

    /**
     * Mutable state for contact list
     */
    var touchBaseContactList = mutableStateListOf<Contact>()

    /**
     * Mutable state for field list
     */
    var currentContactFieldList = mutableStateListOf<SimpleField>()

    /**
     * Mutable states for removing items
     */
    var currentContactID = mutableStateOf(0)

    /**
     * Mutable states for adding a new contact to the database.
     */
    var newContactFirstName     by mutableStateOf("Ryan")
    var newContactLastName      by mutableStateOf("May")
    var newContactRelation      by mutableStateOf(Relation.ImmediateFamily)
    var newContactImage         by mutableStateOf(BitmapFactory.decodeResource(context.resources, R.drawable.xavier))

    /**
     * Mutable states for adding a new field to the database.
     */
    var newFieldTitle       by mutableStateOf(Titles.Email)
    var newFieldContents    by mutableStateOf("")
    var currentContactField by mutableStateOf(SimpleField(Titles.NA, ""))


    init {
        Log.v(TAG,"TouchBase Viewmodel Loaded")
        // Makes Database
        this.db = Room.databaseBuilder(
            context,
            CONTACT_DATABASE::class.java, "contact-db"
        ).allowMainThreadQueries().build()
        this.dao = this.db.getDAO()
        this.dd = DatabaseDriver(this.db)
        this.refreshContactList()
        this.currentContactFieldList.clear()
    }

    fun onEvent(event: TouchBaseEvent){
        when(event){
            TouchBaseEvent.AddContact -> {
                Log.v(TAG,"Add Contact Event")
                this.addContact()
            }
            TouchBaseEvent.UpdateContact -> {
                Log.v(TAG,"Update Event")
                this.refreshContactFields()
            }
            TouchBaseEvent.DeleteContact -> {
                Log.v(TAG,"Delete Event")
                this.removeContact()
            }
            TouchBaseEvent.CameraOpen -> {
                Log.v(TAG,"Camera Open Event")
            }
            TouchBaseEvent.CameraTakePic -> {
                Log.v(TAG,"Camera Take Pic Event")
                this.updatePicture()
            }
            TouchBaseEvent.AddContactField -> {
                Log.v(TAG,"Adding a contact field")
                this.addContactField()
            }
            TouchBaseEvent.DeleteContactField -> {
                Log.v(TAG,"Deleting a contact field")
                this.deleteContactField()
            }
            is TouchBaseEvent.ProfileSelected -> {
                Log.v(TAG,"Profile Selected -> Id=${event.id}")
                this.currentContactID.value = event.id
                this.refreshContactFields()
            }
        }
    }
    private fun updatePicture(){

    }
    private fun deleteContactField(){
        this.dd.deleteContactField(this.currentContactID.value, this.currentContactField)
        this.refreshContactFields()
    }
    private fun addContactField(){
        val newField = SimpleField(
            this.newFieldTitle,
            this.newFieldContents
        )
        this.dd.addNewContactField(this.currentContactID.value, newField)
        this.refreshContactFields()
        this.dd.logContactFields("Database", this.currentContactID.value)
    }

    private fun refreshContactFields(){
        this.currentContactFieldList = mutableStateListOf()
        this.currentContactFieldList.clear()
        this.dd.getContactFields(this.currentContactID.value).forEach {
            this.currentContactFieldList.add(it)
        }
    }
    private fun addContact(){
        this.dd.addNewContact(
            this.newContactFirstName,
            this.newContactLastName,
            this.newContactImage,
            this.newContactRelation)
        this.refreshContactList()
        Log.d(TAG, "Added contact via database DAO.")
    }

    private fun refreshContactList(){
        this.touchBaseContactList = mutableStateListOf()
        this.touchBaseContactList.clear()
        this.dd.getContactList().forEach {
            this.touchBaseContactList.add(
                it,
            )
        }
    }

    private fun removeContact(){
        this.dd.removeContact(this.currentContactID.value)
        this.touchBaseContactList = mutableStateListOf()
        this.dd.getContactList().forEach {
            this.touchBaseContactList.add(
                it,
            )
        }
        Log.d(TAG, "Removed contact via database DAO.")
    }


}