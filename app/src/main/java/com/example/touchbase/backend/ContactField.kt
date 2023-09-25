package com.example.touchbase.backend

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

typealias URL           = String
typealias PhoneNumber   = String
typealias Email         = String
enum class SocialService {
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
    var Title : String
}
@Serializable
data class ImageField(
    override var Title  : String = "",
    var Image           : String = ""
) : ContactField {
    fun serialize() : String {
        return Json.encodeToString<ImageField>(this)
    }
    fun deserialise(serial : String) {
        val decoded = Json.decodeFromString<ImageField>(serial)
        this.Title = decoded.Title
        this.Image = decoded.Image
    }
}
@Serializable
data class SimpleField(
    override var Title  : String = "",
    var Content         : String = "",
) : ContactField {
    fun serialize() : String {
        return Json.encodeToString<SimpleField>(this)
    }
    fun deserialise(serial : String) {
        val decoded = Json.decodeFromString<SimpleField>(serial)
        this.Title = decoded.Title
        this.Content = decoded.Content
    }
}
@Serializable
data class RelationField(
    override var Title  : String = "",
    var Relationship    : Relation = Relation.Other
) : ContactField {
    fun serialize() : String {
        return Json.encodeToString<RelationField>(this)
    }
    fun deserialise(serial : String) {
        val decoded = Json.decodeFromString<RelationField>(serial)
        this.Title = decoded.Title
        this.Relationship = decoded.Relationship
    }
}
@Serializable
data class PhoneNumberField(
    override var Title  : String = "",
    var Description     : String = "",
    var Phone           : PhoneNumber = ""
) : ContactField {
    fun serialize() : String {
        return Json.encodeToString<PhoneNumberField>(this)
    }
    fun deserialise(serial : String) {
        val decoded = Json.decodeFromString<PhoneNumberField>(serial)
        this.Title = decoded.Title
        this.Description = decoded.Description
        this.Phone = decoded.Phone
    }
}
@Serializable
data class EmailField(
    override var Title  : String = "",
    var Description     : String = "",
    var EmailAddress    : Email = ""
) : ContactField {
    fun serialize() : String {
        return Json.encodeToString<EmailField>(this)
    }
    fun deserialise(serial : String) {
        val decoded = Json.decodeFromString<EmailField>(serial)
        this.Title = decoded.Title
        this.Description = decoded.Description
        this.EmailAddress = decoded.EmailAddress
    }
}
@Serializable
data class SocialField(
    override var Title  : String = "",
    var Description     : String = "",
    var Service         : SocialService = SocialService.Facebook,
    var InternetAddress : URL = ""
) : ContactField {
    fun serialize() : String {
        return Json.encodeToString<SocialField>(this)
    }
    fun deserialise(serial : String) {
        val decoded = Json.decodeFromString<SocialField>(serial)
        this.Title = decoded.Title
        this.Description = decoded.Description
        this.Service = decoded.Service
        this.InternetAddress = decoded.InternetAddress
    }
}
