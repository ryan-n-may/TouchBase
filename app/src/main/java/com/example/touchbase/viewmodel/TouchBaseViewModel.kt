package com.example.touchbase.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.touchbase.R
import com.example.touchbase.backend.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

const val TAG = "TouchBaseViewModel"

class TouchBaseViewModel(context: Context) : ViewModel() {
    private var db : CONTACT_DATABASE
    private var dao : CONTACT_DAO
    private lateinit var dd : DatabaseDriver
    private lateinit var context : Context
    lateinit var cameraExecute : ExecutorService
    /**
     * Mutable state for contact list
     */
    var touchBaseContactList    = mutableStateListOf<Contact>()

    /**
     * Mutable state for field list
     */
    var currentContactFieldList = mutableStateListOf<SimpleField>()

    /**
     * Mutable states for current contact items
     */
    var currentContactID        = mutableStateOf(0)
    var currentContactImage     = mutableStateOf(BitmapFactory.decodeByteArray(ByteArray(0), 0, 0))
    var currentContactFirstName = mutableStateOf("")
    var currentContactLastName  = mutableStateOf("")

    /**
     * Mutable states for adding a new contact to the database.
     */
    var newContactID            = mutableStateOf(0)
    var newContactFirstName     = mutableStateOf("Ryan")
    var newContactLastName      = mutableStateOf("May")
    var newContactRelation      = mutableStateOf(Relation.ImmediateFamily)
    var newContactImage         = mutableStateOf(BitmapFactory.decodeResource(context.resources, R.drawable.xavier))

    /**
     * Mutable states for adding a new field to the database.
     */
    var newFieldTitle           = mutableStateOf(Titles.Email)
    var newFieldContents        = mutableStateOf("")

    var currentFieldTitle       = mutableStateOf(Titles.Email)
    var currentFieldContents    = mutableStateOf("")
    var currentContactField     = mutableStateOf(SimpleField(Titles.NA, ""))


    init {
        Log.v(TAG,"TouchBase Viewmodel Loaded")
        // Makes Database
        this.context = context
        this.cameraExecute = Executors.newSingleThreadExecutor()
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
                this.refreshContactList()
            }
            TouchBaseEvent.UpdateContact -> {
                Log.v(TAG,"Update Event")
                this.refreshContactFields()
                this.refreshContactList()
            }
            TouchBaseEvent.DeleteContact -> {
                Log.v(TAG,"Delete Event")
                this.removeContact()
                this.refreshContactList()
            }
            TouchBaseEvent.AddContactField -> {
                Log.v(TAG,"Adding a contact field")
                this.addContactField()
            }
            TouchBaseEvent.DeleteContactField -> {
                Log.v(TAG,"Deleting a contact field")
                this.deleteContactField()
            }
            TouchBaseEvent.AddPhoto -> {
                Log.v(TAG, "Adding a photo")
                this.updatePicture()
                this.refreshContactList()
            }
            is TouchBaseEvent.ProfileSelected -> {
                Log.v(TAG,"Profile Selected -> Id=${event.id}")
                this.currentContactID.value = event.id
                val contactDetails = this.dd.getContactDetails(event.id)
                this.currentContactImage.value = contactDetails.first
                this.currentContactFirstName.value = contactDetails.second
                this.currentContactLastName.value = contactDetails.third
                this.refreshContactFields()
            }
            else -> Log.e(TAG, "UNKNOWN EVENT: ${event}")
        }
    }
    private fun updatePicture(){
        Log.d(TAG, "Updating picture.")
        this.dd.updateContactImage(this.currentContactID.value, this.newContactImage.value)
        this.refreshContactFields()
    }
    private fun deleteContactField(){
        this.dd.deleteContactField(this.currentContactID.value, this.currentContactField.value)
        this.refreshContactFields()
    }
    private fun addContactField(){
        val newField = SimpleField(
            this.newFieldTitle.value,
            this.newFieldContents.value
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
        val contactDetails = this.dd.getContactDetails(this.currentContactID.value)
        this.currentContactImage.value = contactDetails.first
        this.currentContactFirstName.value = contactDetails.second
        this.currentContactLastName.value = contactDetails.third
    }
    private fun addContact(){
        this.dd.addNewContact(
            this.newContactFirstName.value,
            this.newContactLastName.value,
            this.newContactImage.value,
            this.newContactRelation.value)
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

    fun handleImageCapture(bitmap: Bitmap){
        Log.d("ImageCapture", "Image captured")
        this.newContactImage.value = bitmap
        onEvent(TouchBaseEvent.AddPhoto)
    }

    fun handleImageError(e : Exception){

    }
}