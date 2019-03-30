package sandip.example.com.databinding.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import sandip.example.com.databinding.objects.MovieDetailsResponse
import sandip.example.com.databinding.repo.AppRepository
import sandip.example.com.databinding.utils.remoteUtils.AbsentedLiveData
import sandip.example.com.databinding.utils.remoteUtils.Resource
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    var repo: AppRepository
) : ViewModel() {

    private val _refID: MutableLiveData<RepoID> = MutableLiveData()
    val refID: LiveData<RepoID>
        get() = _refID

    val result: LiveData<Resource<MovieDetailsResponse>> = Transformations
        .switchMap(_refID) { input ->
            input.ifExists { id ->
                repo.fetchMovieDetails(id = id)
            }
        }

    fun init(refID: Int) {
        val update = RepoID(refID)
        if (_refID.value == update) return
        _refID.postValue(update)
    }



    data class RepoID(val refID: Int) {
        fun <T> ifExists(f: (Int) -> LiveData<T>): LiveData<T> {
            return if (refID==0) {
                AbsentedLiveData.create()
            } else {
                f(refID)
            }
        }
    }
}