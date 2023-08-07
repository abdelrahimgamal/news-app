package com.arplus.newsapp.utils

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.datastore.preferences.remove
import androidx.datastore.preferences.toPreferences
import com.arplus.newsapp.App
import com.arplus.newsapp.utils.PrefConstants.PAGE
import com.arplus.newsapp.utils.PrefConstants.PREF_STORE_NAME
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


private lateinit var dataStore: DataStore<Preferences>

object PrefConstants {
    const val PREF_STORE_NAME = "news"
    const val PAGE = "page"
}

private fun setupDataStore(context: Context) {
    dataStore = context.createDataStore(name = PREF_STORE_NAME)
}

suspend fun saveInt(context: Context, key: String, value: Int) {
    setupDataStore(context)
    val dataStoreKey = preferencesKey<Int>(key)
    dataStore.edit { settings ->
        settings[dataStoreKey] = value
    }
}


suspend fun readInt(context: Context, key: String): Int? {
    setupDataStore(context)
    val dataStoreKey = preferencesKey<Int>(key)
    val preferences = dataStore.data.first().toPreferences()
    return preferences[dataStoreKey]
}


suspend fun deleteSavedData(context: Context, key: String?) {
    setupDataStore(context)
    dataStore.edit {
        it.remove(preferencesKey<String>(key!!))
    }
}


fun savePage(page: Int) {
    runBlocking { saveInt(App.applicationContext(), PAGE, page) }
}


fun getPage(): Int {
    return runBlocking {
        readInt(App.applicationContext(), PAGE) ?: 1
    }
}

