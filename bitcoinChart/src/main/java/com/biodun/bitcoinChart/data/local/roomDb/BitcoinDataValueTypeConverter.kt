package com.biodun.bitcoinChart.data.local.roomDb

import androidx.room.TypeConverter
import com.biodun.bitcoinChart.data.model.BitcoinDataValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Collections

class BitcoinDataValueTypeConverter {

    private val gson = Gson()

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
}
