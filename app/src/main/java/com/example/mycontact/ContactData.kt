package com.example.mycontact

data class ContactData(val id: String, var name: String, val phone: String, val gender: Gender)
enum class Gender {
    MEN, WOMEN, UNKNOWN
}
