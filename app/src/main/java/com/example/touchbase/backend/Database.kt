package com.example.touchbase.backend

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.room.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Converters {
    @TypeConverter
    fun toContactField(json : String) : SimpleField {
        Log.d(TAG, "Deserializing simple field.")
        val newContactField = SimpleField()
        newContactField.deserialize(json)
        return newContactField
    }
    @TypeConverter
    fun fromContactField(field : SimpleField) : String {
        Log.d(TAG, "Serializing simple field.")
        return field.serialize()
    }

    @TypeConverter
    fun fromImage(image : Bitmap) : String {
        Log.d(TAG, "Serializing image.")
        val stream = ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        val byteArr = stream.toByteArray();
        val string = byteArr.toString(Charsets.ISO_8859_1)
        return string
    }

    @TypeConverter
    fun toImage(string : String) : Bitmap? {
        Log.d(TAG, "Deserializing image.")
        val byteArray = string.toByteArray(Charsets.ISO_8859_1)
        val image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size);
        return image
    }
}

const val CONTACT_LIST = "CONTACT_LIST_TABLE"
const val FIELD_LIST = "FIELD_LIST_TABLE"

@Entity(tableName = CONTACT_LIST)
class CONTACT_TABLE(
    @PrimaryKey val id  : Int,
    val firstName       : String,
    val lastName        : String,
    val image           : Bitmap,
    val relation        : String
)

@Entity(tableName = FIELD_LIST)
class FIELD_TABLE(
    @PrimaryKey(autoGenerate = true)
    val fieldID: Int,
    val id : Int,
    val field : SimpleField
)
@Database(entities = [CONTACT_TABLE::class, FIELD_TABLE::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CONTACT_DATABASE : RoomDatabase() {
    abstract fun getDAO() : CONTACT_DAO
}
@Dao
interface CONTACT_DAO {
    @Query("DELETE FROM $CONTACT_LIST")
    fun clearContactList()
    @Query("DELETE FROM $FIELD_LIST")
    fun clearFieldList()
    /**
     * Mutators: ADD AND REMOVE
     */
    // Insert new contact into database
    @Query("INSERT INTO $CONTACT_LIST (id, firstName, lastName, image, relation) " +
            "VALUES (:contactID, :firstName, :lastName, :image, :relation)")
    fun addNewContact(
        contactID   : Int?,
        firstName   : String,
        lastName    : String,
        image       : Bitmap,
        relation    : String,
    )
    @Query("DELETE FROM $CONTACT_LIST WHERE id == :id")
    fun removeContact(
        id   : Int?,
    )
    @Query("DELETE FROM $FIELD_LIST WHERE id == :id")
    fun removeContactFields(
        id   : Int?,
    )
    // Insert new field into field table
    @Query("INSERT INTO $FIELD_LIST (id, field) " +
            "VALUES (:id, :field)")
    fun addNewField(
        id      : Int,
        field   : SimpleField
    )

    @Query("DELETE FROM $FIELD_LIST WHERE id == :id AND field == :field")
    fun removeContactField(
        id      : Int,
        field   : SimpleField
    )
    /**
     * Accessors
     */
    /** Accessors that get all the users with a broad filter, or sorting **/
    @Query("SELECT id FROM $CONTACT_LIST ORDER BY firstName DESC")
    fun getAllContacts_OrderByFirstNameDesc() : List<Int>
    @Query("SELECT id FROM $CONTACT_LIST ORDER BY firstName ASC")
    fun getAllContacts_OrderByFirstNameAsc() : List<Int>
    @Query("SELECT id FROM $CONTACT_LIST ORDER BY lastName DESC")
    fun getAllContacts_OrderByLastNameDesc() : List<Int>
    @Query("SELECT id FROM $CONTACT_LIST ORDER BY lastName ASC")
    fun getAllContacts_OrderByLastNameAsc() : List<Int>
    @Query("SELECT id FROM $CONTACT_LIST WHERE relation == :relation")
    fun getAllContacts_FilterByRelation(relation : String) : List<Int>
    /** Accessors that search for users **/
    @Query("SELECT id from $CONTACT_LIST WHERE firstName == :searchName OR lastName == :searchName")
    fun searchByFirstOrLastName(searchName : String) : List<Int>
    /** Accessors that fetch a value by ID **/
    @Query("SELECT firstName from $CONTACT_LIST WHERE id == :id")
    fun fetchFirstName(id : Int) : String
    @Query("SELECT lastName from $CONTACT_LIST WHERE id == :id")
    fun fetchLastName(id : Int) : String
    @Query("SELECT image from $CONTACT_LIST WHERE id == :id")
    fun fetchImage(id : Int) : Bitmap
    /** Accessors that fetch all the contact cards for a given ID **/
    @Query("SELECT relation from $CONTACT_LIST WHERE id == :id")
    fun fetchRelation(id : Int) : String
    /** Accessors that fetch all the contact cards for a given ID **/
    @Query("SELECT field from $FIELD_LIST WHERE id == :id")
    fun fetchAllFields(id : Int) : List<SimpleField>
    /**
     * MUTATORS
     */
    /** Update contact image **/
    @Query("UPDATE $CONTACT_LIST SET image = :bitmap WHERE id == :id")
    fun updateContactImage(id : Int, bitmap : Bitmap)
    /** Update contact field **/
    @Query("UPDATE $FIELD_LIST SET field = :simpleField WHERE fieldID == :fieldID")
    fun updateContactField(fieldID : Int, simpleField : SimpleField)
    /** Update Contact First Name **/
    @Query("UPDATE $CONTACT_LIST SET firstName = :firstName WHERE id == :id")
    fun updateContactFirstName(id : Int, firstName : String)
    /** Update Contact Last Name **/
    @Query("UPDATE $CONTACT_LIST SET firstName = :firstName WHERE id == :id")
    fun updateContactLastName(id : Int, firstName : String)
}