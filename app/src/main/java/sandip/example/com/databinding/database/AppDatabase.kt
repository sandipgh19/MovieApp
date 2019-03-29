package sandip.example.com.databinding.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import sandip.example.com.databinding.data.AppDao
import sandip.example.com.databinding.objects.CategoryResponse
import sandip.example.com.databinding.objects.ValueItem

@Database(
    entities = [
        (CategoryResponse::class),
        (ValueItem::class)],
    version = 1, exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}