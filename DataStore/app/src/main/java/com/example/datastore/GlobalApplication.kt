package com.example.datastore

import android.app.Application

class GlobalApplication: Application() {
    private lateinit var dataStore : DataStoreModule

    companion object {
        private lateinit var sampleApplication: GlobalApplication
        fun getInstance() : GlobalApplication = sampleApplication
    }

    override fun onCreate() {
        super.onCreate()
        sampleApplication = this
        dataStore = DataStoreModule(this)
    }

    fun getDataStore() : DataStoreModule = dataStore
}