/*
package net.corpy.cvtcenter.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class SessionManager(private val _context: Context) {
    // Shared Preferences
    private val pref: SharedPreferences
    // Editor for Shared preferences
    private val editor: SharedPreferences.Editor

    */
/**
     * Get stored session data
     *//*

    val savedUser: User
        get() = User(
            pref.getInt(USER_ID, 0),
            pref.getString(NAME, "") ?: "",
            pref.getString(EMAIL, "") ?: "",
            pref.getString(API_TOKEN, "") ?: "",
            pref.getString(PHONE, "") ?: "",
            pref.getString(CITY, "") ?: "",
            pref.getString(ADDRESS, "") ?: "",
            pref.getString(TYPE, "") ?: "",
            pref.getString(IMAGE, "") ?: "",
            pref.getString(OFFICIAL_DOCUMENT, "")
        )


    */
/**
     * Quick check for login
     *//*

    // Get Login State
    val isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)

    init {
        // Shared pref mode
        pref = _context.getSharedPreferences(PREF_NAME, 0)
        editor = pref.edit()
    }

    */
/**
     * Create login session
     *//*

    fun createLoginSession(user: User) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)
        // Storing name in pref
        editor.putInt(USER_ID, user.id)
        editor.putString(NAME, user.name)
        editor.putString(PASSWORD, user.password)
        editor.putString(EMAIL, user.email)
        editor.putString(API_TOKEN, user.api_token)
        editor.putString(PHONE, user.phone)
        editor.putString(CITY, user.city)
        editor.putString(ADDRESS, user.address)
        editor.putString(TYPE, user.type)
        editor.putString(IMAGE, user.image)
        editor.putString(OFFICIAL_DOCUMENT, user.official_document)

        // commit changes
        editor.commit()
    }

    fun updateImage(image: String) {
        editor.putString(IMAGE, image)
        // commit changes
        editor.commit()
    }

    */
/**
     * Clear session details
     *//*

    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear()
        editor.commit()
        // After logout redirect user to Loing Activity
//        val i = Intent(_context, MainActivity::class.java)
        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // Add new Flag to start new Activity
//        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        // Staring Login Activity
//        _context.startActivity(i)
    }

    companion object {
        // User name (make variable public to access from outside)


        private const val USER_ID = "id"

        private const val NAME = "name"
        private const val PASSWORD = "Password"
        private const val EMAIL = "Email"
        private const val PHONE = "phone"
        private const val CITY = "city"
        private const val ADDRESS = "address"
        private const val TYPE = "TYPE"
        private const val API_TOKEN = "api_token"
        // Sharedpref file name
        private const val PREF_NAME = "LoggingSession"
        // All Shared Preferences Keys
        private const val IS_LOGIN = "IsLoggedIn"
        private const val IMAGE = "IMAGE"
        private const val OFFICIAL_DOCUMENT = "official_document"
    }
}*/
