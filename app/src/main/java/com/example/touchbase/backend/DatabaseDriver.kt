package com.example.touchbase.backend

import android.util.Log
import kotlin.random.Random

const val TAG = "DatabaseDriver"
class DatabaseDriver(db : CONTACT_DATABASE) {
    val db : CONTACT_DATABASE
    val dao : CONTACT_DAO
    init {
        this.db = db
        this.dao = db.getDAO()
    }
    fun addNewContact(
        firstName   : String,
        lastName    : String,
        image       : String = "",
        relation    : Relation,
    ){
        Log.v(TAG, "Adding new contact $firstName $lastName")
        val arr = firstName.chars().toArray()
        var seed = 0
        for(i in 0 .. (arr.size)){
            seed += arr[i]
        }
        val id = Random(seed).nextInt();
        Log.v(TAG, "Random id by seed $seed is $id")
        this.dao.addNewContact(id, firstName, lastName,  image, relation.toString())
    }
}