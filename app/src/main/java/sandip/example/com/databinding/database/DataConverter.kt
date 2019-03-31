package sandip.example.com.databinding.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import sandip.example.com.databinding.objects.MovieGenres

class DataConverter {

    @TypeConverter
    fun listToJson(value: MutableList<MovieGenres?>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String?): MutableList<MovieGenres?>? {

        val objects = Gson().fromJson(value, Array<MovieGenres?>::class.java)
        val list = objects?.toMutableList()
        return list
    }
}