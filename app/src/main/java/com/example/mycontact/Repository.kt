package com.example.mycontact

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Repository(private val localStorage: LocalStorage) {
    private var ls = ArrayList<ContactData>()

    fun getList(): List<ContactData> = getLocalList()

    fun addContact(data: ContactData) {
        ls.add(data)
        saveList(ls)
    }

    fun updateData(data: ContactData, position: Int) {
        ls[position] = data
        saveList(ls)
    }

    fun deleteData(data: ContactData) {
        ls.remove(data)
        saveList(ls)
    }

    private fun saveList(list: List<ContactData>) {
        val lsGson = Gson().toJson(list)
        localStorage.submitList(lsGson)
    }

    private fun getLocalList(): List<ContactData> {
        val gsonList = localStorage.getList()
        val gson = Gson()
        val itemType = object : TypeToken<List<ContactData>>() {}.type
        ls=gson.fromJson(gsonList, itemType)
        return ls
    }
}