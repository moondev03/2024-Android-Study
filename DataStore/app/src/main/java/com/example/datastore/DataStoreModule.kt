package com.example.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore  by preferencesDataStore(name = "dataStore")

class DataStoreModule(private val context: Context) {

    companion object {
        private val LAST_NAME_KEY = stringPreferencesKey("last_name")
        private val FIRST_NAME_KEY = stringPreferencesKey("first_name")
        private val AGE_KEY = intPreferencesKey("age")
        private val GENDER_KEY = booleanPreferencesKey("gender")
    }

    suspend fun storeUser(
        age: Int,
        firstName: String,
        lastName: String,
        isMale: Boolean
    ) {
        context.dataStore.edit {
            it[LAST_NAME_KEY] = lastName
            it[FIRST_NAME_KEY] = firstName
            it[AGE_KEY] = age
            it[GENDER_KEY] = isMale
        }
    }

    // dataStore 값 읽기
    val userAgeFlow: Flow<Int?> = context.dataStore.data.map {
        it[AGE_KEY]
    }

    val userFirstNameFlow: Flow<String?> = context.dataStore.data.map {
        it[FIRST_NAME_KEY]
    }

    val userLastNameFlow: Flow<String?> = context.dataStore.data.map {
        it[LAST_NAME_KEY]
    }

    val userGenderFlow: Flow<Boolean?> = context.dataStore.data.map {
        it[GENDER_KEY]
    }

}