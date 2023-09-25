package com.example.touchbase.backend

import androidx.room.*

class Converters {
    @TypeConverter
    fun toContactField(json : String) : SimpleField {
        var newContactField = SimpleField()
        newContactField.deserialise(json)
        return newContactField
    }

    @TypeConverter
    fun fromContactField(field : SimpleField) : String {
        return field.serialize()
    }
}

const val CONTACT_LIST = "CONTACT_LIST_TABLE"
const val FIELD_LIST = "FIELD_LIST_TABLE"

@Entity(tableName = CONTACT_LIST)
class CONTACT_TABLE(
    @PrimaryKey val id : Int,
    val firstName : String,
    val lastName : String,
    val image : String,
    val relation : String
)

@Entity(tableName = FIELD_LIST)
class FIELD_TABLE(
    @PrimaryKey val id : Int,
    val field : SimpleField
)

@Database(entities = [CONTACT_TABLE::class, FIELD_TABLE::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class CONTACT_DATABASE : RoomDatabase() {
    abstract fun getDAO() : CONTACT_DAO
}

@Dao
interface CONTACT_DAO {
    /** Mutators **/
    // Insert new contact into database
    @Query("INSERT INTO $CONTACT_LIST (id, firstName, lastName, image, relation) " +
            "VALUES (:contactID, :firstName, :lastName, :image, :relation)")
    fun addNewContact(
        contactID   : Int?,
        firstName   : String,
        lastName    : String,
        image       : String,
        relation    : String,
    )
    // Insert new field into field table
    @Query("INSERT INTO $FIELD_LIST (id, field) " +
            "VALUES (:id, :field)")
    fun addNewField(
        id      : Int?,
        field   : SimpleField
    )
    /** Accessors **/
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
    @Query("SELECT id from $CONTACT_LIST WHERE firstName == :searchName OR WHERE lastName == :searchName")
    fun searchByFirstOrLastName(searchName : String) : List<Int>
    /** Accessors that fetch a value by ID **/
    @Query("SELECT firstName from $CONTACT_LIST WHERE id == :id")
    fun fetchFirstName(id : Int) : String
    @Query("SELECT lastName from $CONTACT_LIST WHERE id == :id")
    fun fetchLastName(id : Int) : String
    @Query("SELECT image from $CONTACT_LIST WHERE id == :id")
    fun fetchImage(id : Int) : String
    /** Accessors that fetch all the contact cards for a given ID **/
    @Query("SELECT field from $FIELD_LIST WHERE id == :id")
    fun fetchAllFields(id : Int) : List<SimpleField>
}