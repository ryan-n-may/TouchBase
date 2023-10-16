package com.example.touchbase.viewmodel

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Picture
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.touchbase.R
import com.example.touchbase.backend.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

const val TAG = "TouchBaseViewModel"

class TouchBaseViewModel(
    context: Context,
    requestForCameraPermissionAgain: () -> Unit,
    requestForContactPermissionAgain: () -> Unit,
    ) : ViewModel() {
    private var db : CONTACT_DATABASE
    private var dao : CONTACT_DAO
    private lateinit var dd : DatabaseDriver
    private lateinit var context : Context
    lateinit var cameraExecute : ExecutorService
    /**
     * Callbacks to re-request permissions
     */
    lateinit var requestForCameraPermissionAgain : () -> Unit
    lateinit var requestForContactPermissionAgain : () -> Unit
    lateinit var syncActivity : Activity

    /**
     * Mutable state for contact list
     */
    var touchBaseContactList    = mutableStateListOf<Contact>()
    var mobileContactList       = mutableStateListOf<Contact>()

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


    var test = mutableStateOf("")

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
        this.requestForCameraPermissionAgain = requestForCameraPermissionAgain
        this.requestForContactPermissionAgain = requestForContactPermissionAgain
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
            TouchBaseEvent.EditContact -> {
                Log.v(TAG, "Editing contact")
                this.editContact()
                this.refreshContactList()
            }
            TouchBaseEvent.EditField -> {
                Log.v(TAG, "Editing Field")
                this.editContactField()
                this.refreshContactFields()
            }
            TouchBaseEvent.SyncContacts -> {
                Log.v(TAG, "Syncing mobile contacts")
                this.syncMobileContacts(this.syncActivity)
                this.refreshContactList()
            }
            is TouchBaseEvent.CurrentActivity -> {
                this.syncActivity = event.activity
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

    private fun syncMobileContacts(activity: Activity) : Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) ==
            PackageManager.PERMISSION_GRANTED) {

            val builder = StringBuilder()
            val resolver : ContentResolver = activity.contentResolver;
            val cursor = resolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
            if(cursor == null){
                Log.e(TAG,"ERROR: cursor is null accessing contacts.")
            }else if(cursor.count > 0){
                while(cursor.moveToNext()) {
                    val NAME_COL_INDEX = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val PHONE_COL_INDEX = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    val ID_COL_INDEX = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                    var ID : String = ""
                    var NAME : String = ""
                    var PHONE : Int = 0
                    if(NAME_COL_INDEX > 0 && PHONE_COL_INDEX > 0){
                        ID = cursor.getString(ID_COL_INDEX)
                        NAME = cursor.getString(NAME_COL_INDEX)
                        PHONE = cursor.getString(PHONE_COL_INDEX).toInt()
                    }
                    if(PHONE > 0){
                        val cursorPhone = activity.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            arrayOf(ID), null
                        )
                        if(cursorPhone != null && cursorPhone.count > 0){
                            while(cursorPhone.moveToNext()){
                                val cursorPhoneIndex = cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                if(cursorPhoneIndex != -1){
                                    val db = ContextCompat.getDrawable(context, R.drawable.anna)
                                    val bit = Bitmap.createBitmap(
                                        db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
                                    )
                                    val phoneNumValue = cursorPhone.getString(cursorPhoneIndex)
                                    val id = this.dd.addNewContact(NAME, "", bit, Relation.Other)
                                    val newField = SimpleField(Titles.Mobile, phoneNumValue.toString())
                                    this.dd.addNewContactField(id!!, newField)
                                    /*builder.append("Contact: ").append(NAME).append(", Phone Number: ").append(
                                        phoneNumValue).append("\n\n")*/
                                }
                            }
                            cursorPhone.close()
                            this.refreshContactList()
                            return true
                        }
                    } else {
                        Log.e(TAG, "No Phone Available")
                        return false
                    }
                }
                cursor.close()
                this.refreshContactList()
                return true
            }
        } else {
            // if permission not granted requesting permission .
            this.requestForContactPermissionAgain()
            return false
        }
        return false
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

    fun refreshContactFields(){
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

    fun refreshContactList(){
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

    private fun editContact(){
        Log.d(TAG, "editContact() called.")
        this.dd.updateContactName(
            this.currentContactID.value,
            this.currentContactFirstName.value,
            this.currentContactLastName.value
            )
        this.dd.updateContactImage(
            this.currentContactID.value,
            this.currentContactImage.value
        )
    }

    private fun editContactField(){
        Log.d(TAG, "editContactField() called.")
        val fieldID = this.dd.getContactFieldID(this.currentContactID.value, this.currentContactField.value)
        val newField = SimpleField(this.currentFieldTitle.value, this.currentFieldContents.value)
        this.dd.editContactField(fieldID, newField)
    }

    fun handleImageCapture(bitmap: Bitmap){
        Log.d("ImageCapture", "Image captured")
        this.newContactImage.value = bitmap
        onEvent(TouchBaseEvent.AddPhoto)
    }

    fun handleImageError(e : Exception){

    }


}