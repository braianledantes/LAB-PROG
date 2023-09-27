package com.braianledantes.elbardelafai

import android.app.Application
import com.braianledantes.elbardelafai.database.ElBarDeLaFAIDatabase

class App: Application() {

    val database: ElBarDeLaFAIDatabase by lazy {
        ElBarDeLaFAIDatabase.getDatabase(this)
    }
}