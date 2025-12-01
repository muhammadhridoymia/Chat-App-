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

    suspend fun saveEmail(context: Context, email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    fun getEmail(context: Context): Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[EMAIL_KEY] ?: ""
        }
    }
    suspend fun clearEmail(context: Context){
        context.dataStore.edit{it.clear()}
    }

}
