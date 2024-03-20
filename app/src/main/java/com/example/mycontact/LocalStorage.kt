package com.example.mycontact

import android.content.Context
import android.content.SharedPreferences

class LocalStorage(private val context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("local-storage", Context.MODE_PRIVATE)
    private val keyList = "contact_list"
    fun submitList(jsonList: String) {
        pref.edit().apply {
            putString(keyList, jsonList)
            apply()
        }
    }

    fun getList(): String {
        return pref.getString(keyList, emptyList) ?: emptyList
    }

    private val emptyList = """[]"""
}