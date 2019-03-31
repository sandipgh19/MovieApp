package sandip.example.com.databinding.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import sandip.example.com.databinding.objects.MovieDetailsResponse
import sandip.example.com.databinding.objects.MovieListItem

@Dao
interface AppDao {

    @Query("SELECT * from movie_list order by popularity desc")
    fun fetchMovieList(): LiveData<List<MovieListItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(list: List<MovieListItem?>?)

    @Query("DELETE FROM movie_list")
    fun deleteMovies()

    @Transaction
    fun deleteAndInsertMovieList(listItem: List<MovieListItem?>?) {
        deleteMovies()
        insertMovies(list = listItem)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(item: MovieDetailsResponse)


    @Query("SELECT * FROM movie_details where id=:id")
    fun fetchMovieDetails(id: Int) :LiveData<MovieDetailsResponse>

}