package sandip.example.com.databinding.repo

import android.arch.lifecycle.LiveData
import sandip.example.com.databinding.data.AppDao
import sandip.example.com.databinding.objects.MovieDetailsResponse
import sandip.example.com.databinding.objects.MovieListItem
import sandip.example.com.databinding.objects.MovieListResponse
import sandip.example.com.databinding.remote.WebServices
import sandip.example.com.databinding.utils.helperUtils.AppExecutors
import sandip.example.com.databinding.utils.remoteUtils.NetworkBoundResource
import sandip.example.com.databinding.utils.remoteUtils.Resource
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val webservice: WebServices,
    private val executor: AppExecutors,
    private val dao: AppDao) {

    val api_key = "153625bb97b19df233040bf26f3399ab"


    fun fetchMovieList(): LiveData<Resource<List<MovieListItem>>> {
        return object : NetworkBoundResource<List<MovieListItem>, MovieListResponse>(executor) {
            override fun saveCallResult(item: MovieListResponse) {

                item.results.let {
                    dao.deleteAndInsertMovieList(listItem = it)
                }
            }
            override fun shouldFetch(data: List<MovieListItem>?) = true
            override fun loadFromDb() = dao.fetchMovieList()
            override fun createCall() = webservice.getMovieList(api_key = api_key)
        }.asLiveData()
    }

    fun fetchMovieDetails(id: Int): LiveData<Resource<MovieDetailsResponse>> {
        return object : NetworkBoundResource<MovieDetailsResponse, MovieDetailsResponse>(executor) {
            override fun saveCallResult(item: MovieDetailsResponse) {
                dao.insertMovieDetails(item = item)
            }
            override fun shouldFetch(data: MovieDetailsResponse?) = true
            override fun loadFromDb() = dao.fetchMovieDetails(id = id)
            override fun createCall() = webservice.fetchMovieDetails(id = id, api_key = api_key)
        }.asLiveData()
    }

}