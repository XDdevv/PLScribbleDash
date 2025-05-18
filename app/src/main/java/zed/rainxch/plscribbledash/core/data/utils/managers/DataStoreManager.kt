package zed.rainxch.plscribbledash.core.data.utils.managers

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import zed.rainxch.plscribbledash.core.data.utils.constants.PreferenceKeys.COINS
import zed.rainxch.plscribbledash.core.data.utils.constants.PreferenceKeys.EQUIPPED_CANVAS_ID
import zed.rainxch.plscribbledash.core.data.utils.constants.PreferenceKeys.EQUIPPED_PEN_ID
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val equippedCanvasId: Flow<Int> = dataStore.data
        .map { it[EQUIPPED_CANVAS_ID] ?: -1 }

    val equippedPenId: Flow<Int> = dataStore.data
        .map { it[EQUIPPED_PEN_ID] ?: -1 }

    val coins: Flow<Int> = dataStore.data
        .map { it[COINS] ?: 0 }

    suspend fun setEquippedCanvasId(id: Int) {
        dataStore.edit { prefs ->
            prefs[EQUIPPED_CANVAS_ID] = id
        }
    }

    suspend fun setEquippedPenId(id: Int) {
        dataStore.edit { prefs ->
            prefs[EQUIPPED_PEN_ID] = id
        }
    }

    suspend fun setCoins(coins: Int) {
        dataStore.edit { prefs ->
            prefs[COINS] = coins
        }
    }

}