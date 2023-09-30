package com.example.touchbase.backend

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

enum class Titles {
    NA,
    /** Regular Contacts **/
    Email,
    Mobile,
    WorkPhone,
    HomePhone,
    /** Relationship **/
    Company,
    Relationship,
    /** Social Services **/
    Facebook,
    Instagram,
    Twitter,
    Threads,
    Reddit,
    TikTok,
    Discord,
    LinkedIn,
    GitHub,
    Steam,
    Telegram,
    Line,
}
enum class Relation {
    ImmediateFamily,
    ExtendedFamily,
    Friend,
    ProfessionalAssociate,
    Other
}

interface ContactField {
    var Title : Titles
}

@Serializable
data class SimpleField(
    override var Title  : Titles = Titles.NA,
    var Content         : String = "",
) : ContactField {
    fun serialize() : String {
        return "${Title}~$Content"
    }
    fun deserialize(serial : String) {
        val split = serial.split("~")
        this.Title = Titles.valueOf(split[0])
        this.Content = split[1]
    }
}