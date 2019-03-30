package sandip.example.com.databinding.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import sandip.example.com.databinding.objects.MovieListItem
import sandip.example.com.databinding.repo.AppRepository
import sandip.example.com.databinding.utils.remoteUtils.AbsentedLiveData
import sandip.example.com.databinding.utils.remoteUtils.Resource
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    var repo: AppRepository
) : ViewModel() {


    private val _refID: MutableLiveData<RepoID> = MutableLiveData()
    val refID: LiveData<RepoID>
        get() = _refID

    val result: LiveData<Resource<List<MovieListItem>>> = Transformations
        .switchMap(_refID) { input ->
            input.ifExists { category ->
                repo.fetchMovieList()
            }
        }

    fun init(refID: String) {
        val update = RepoID(refID)
        if (_refID.value == update) return
        _refID.postValue(update)
    }



    data class RepoID(val refID: String) {
        fun <T> ifExists(f: (String) -> LiveData<T>): LiveData<T> {
            return if (refID.isBlank()) {
                AbsentedLiveData.create()
            } else {
                f(refID)
            }
        }
    }
}