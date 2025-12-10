package  com.example.chatapp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore (name = "datastore")

object LoginDataStore {
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val NAME_KEY = stringPreferencesKey("name")
    private val ID_KEY = stringPreferencesKey("id")


    suspend fun userSave(context: Context, name : String,email: String,id: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[EMAIL_KEY] = email
            preferences[ID_KEY] = id
        }
    }

    fun getName(context: Context): Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[NAME_KEY] ?: ""
        }
    }
    fun  getId(context: Context): Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[ID_KEY] ?: ""
        }
    }
    suspend fun clearEmail(context: Context){
        context.dataStore.edit{it.clear()}
    }

}
