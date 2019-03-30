package sandip.example.com.databinding.remote

import android.arch.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import sandip.example.com.databinding.objects.MovieDetailsResponse
import sandip.example.com.databinding.objects.MovieListResponse
import sandip.example.com.databinding.utils.remoteUtils.ApiResponse

interface WebServices {

    @GET("discover/movie")
    fun getMovieList(@Query("api_key") api_key: String): LiveData<ApiResponse<MovieListResponse>>


    @GET("movie/{id}")
    fun fetchMovieDetails(@Path("id") id: Int,
                          @Query("api_key") api_key: String) : LiveData<ApiResponse<MovieDetailsResponse>>


}