package com.example.coinclubapp.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager (activity: Context){

    private val ID = "ID"
    private val NAME = "name"

    private var editor: SharedPreferences.Editor? = null

    var pref: SharedPreferences? = null

    init {
        pref = activity.getSharedPreferences("newProject", 0)
        editor = pref?.edit()
    }

    //initial load market place verification
    
    fun setID(state: String) { editor?.putString(ID, state)?.apply() }
    fun getID(): String? { return pref?.getString(ID, ID) }

    fun setName(state: String) { editor?.putString(NAME, state)?.apply() }
    fun getName(): String? { return pref?.getString(NAME, NAME) }
}