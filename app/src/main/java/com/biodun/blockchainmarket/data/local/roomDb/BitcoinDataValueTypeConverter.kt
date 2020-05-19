package com.biodun.blockchainmarket.data.local.roomDb

import androidx.room.TypeConverter
import com.biodun.blockchainmarket.data.model.BitcoinDataValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Collections

class BitcoinDataValueTypeConverter {

    private val gson =
        GsonWrapper.getGson()

    @TypeConverter
    fun fromBitcoinDataValueToString(data: List<BitcoinDataValue>): String = gson.toJson(data)

    @TypeConverter
    fun stringToBitcoinDataValue(data: String?): List<BitcoinDataValue> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType: Type = object : TypeToken<List<BitcoinDataValue>>() {}.type
        return gson.fromJson(data, listType)
    }

    companion object {
        object GsonWrapper {
            fun getGson() = Gson()
        }
    }
}
