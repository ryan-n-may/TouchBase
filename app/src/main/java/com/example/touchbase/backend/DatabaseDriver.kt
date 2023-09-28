package com.example.touchbase.backend

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import kotlin.random.Random

val TAG = "DatabaseDriver"

data class Contact(
    var id  : Int,
    var firstName : String,
    var lastName : String,
    var image : String
)

class DatabaseDriver(db : CONTACT_DATABASE) {
    val db : CONTACT_DATABASE
    val dao : CONTACT_DAO
    init {
        this.db = db
        this.dao = db.getDAO()
    }

    /**
     * LOGGING METHODS
     */
    fun logContacts(TAG : String){
        val contacts = this.getContactList()
        for(i in contacts.indices){
            val currentContract = contacts[i]
            Log.d(TAG, "> ${currentContract.id}, ${currentContract.firstName}, ${currentContract.lastName}")
        }
    }
    fun logContactFields(TAG : String, id : Int){
        val fields = this.dao.fetchAllFields(id)
        for(i in fields.indices){
            val field = fields[i]
            val title = field.Title
            val content = field.Content
            Log.d(TAG, "> $title : $content")
        }
    }

    /**
     * FETCH CONTACT FIELDS
     *  Get the fields for a contact given by a contact ID
     */
    fun getContactFields(id : Int) : List<SimpleField> {
        return this.dao.fetchAllFields(id)
    }

    /**
     * DELETE DATABASE
     *  clear the database for contacts and contact fields
     */
    fun clearDatabases() {
        this.dao.clearContactList()
        this.dao.clearFieldList()
    }

    /**
     * Return a list of all contacts (first name + last name, ID, and image)
     */
    fun getContactList() : MutableList<Contact>{
        val listIDs = this.dao.getAllContacts_OrderByFirstNameDesc()
        var listContacts : MutableList<Contact> = mutableListOf()
        var firstName   : String
        var lastName    : String
        var image       : String
        var id          : Int
        for(i in listIDs.indices){
            id          = listIDs[i]
            firstName   = this.dao.fetchFirstName(id)
            lastName    = this.dao.fetchLastName(id)
            image       = this.dao.fetchImage(id)
            var newContract = Contact(id, firstName, lastName, image)
            /** Create contact obj here **/
            /** Add contact obj to list instead of tripple **/
            listContacts.add(newContract)
        }
        return listContacts
    }

    /**
     * Add a new contact field
     */
    fun addNewContactField(
        id      : Int,
        field   : SimpleField
    ){
        this.dao.addNewField(id, field)
    }

    /**
     * Add a new contact
     */
    fun addNewContact(
        firstName   : String,
        lastName    : String,
        image       : String = "",
        relation    : Relation,
    ) : Boolean {
        Log.v(TAG, "Adding new contact $firstName $lastName")
        val arr = firstName.chars().toArray()
        var seed = 0
        for (i in 0 until (firstName.length)) {
            seed += arr[i]
        }
        val id = Random(seed).nextInt();
        Log.v(TAG, "Random id by seed $seed is $id")
        try{
            this.dao.addNewContact(id, firstName, lastName, image, relation.toString())
        } catch (e : SQLiteConstraintException){
            return false
        }
        return true
    }

    /**
     * Remove contact
     */
    fun removeContact(id : Int){
        this.dao.removeContact(id)
        this.dao.removeContactFields(id)
    }
}