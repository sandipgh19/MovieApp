package sandip.example.com.databinding.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import sandip.example.com.databinding.data.AppDao
import sandip.example.com.databinding.objects.MovieDetailsResponse
import sandip.example.com.databinding.objects.MovieListItem

@Database(
    entities = [
        (MovieListItem::class),
        (MovieDetailsResponse::class)],
    version = 1, exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}