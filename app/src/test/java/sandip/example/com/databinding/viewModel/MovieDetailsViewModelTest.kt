package sandip.example.com.databinding.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import sandip.example.com.databinding.objects.MovieDetailsResponse
import sandip.example.com.databinding.repo.AppRepository
import sandip.example.com.databinding.utils.remoteUtils.Resource

@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    @Rule
    @JvmField
    val intantExecutor = InstantTaskExecutorRule()

    val repo = Mockito.mock(AppRepository::class.java)
    val repoViewModel = MovieDetailsViewModel(repo = repo)

    @Test
    fun testNull() {
        MatcherAssert.assertThat(repoViewModel.result, CoreMatchers.notNullValue())
        Mockito.verify(repo, Mockito.never()).fetchMovieDetails(id = 123)
    }

    @Test
    fun dontFetchWithoutObserver() {
        repoViewModel.init(refID = 123)
        Mockito.verify(repo, Mockito.never()).fetchMovieDetails(id = 123)
    }

    @Test
    fun fetchWithObserver() {
        repoViewModel.init(123)
        repoViewModel.result.observeForever(mock())
        Mockito.verify(repo).fetchMovieDetails(id = 123)
    }

    @Test
    fun fetchWhenObserve() {
        repoViewModel.result.observeForever(mock())
        repoViewModel.init(123)
        Mockito.verify(repo).fetchMovieDetails(id = 123)
    }

    @Test
    fun blankRefID() {
        repoViewModel.init(0)
        val observer = mock<Observer<Resource<MovieDetailsResponse>>>()
        repoViewModel.result.observeForever(observer)
        Mockito.verify(observer).onChanged(null)
    }

    @Test
    fun resetSameQuery() {
        val observer = mock<Observer<MovieDetailsViewModel.RepoID>>()
        repoViewModel.refID.observeForever(observer)
        Mockito.verifyNoMoreInteractions(observer)
        repoViewModel.init(123)
        Mockito.verify(observer).onChanged(MovieDetailsViewModel.RepoID(123))
        Mockito.reset(observer)
        repoViewModel.init(123)
        Mockito.verifyNoMoreInteractions(observer)
        repoViewModel.init(145)
        Mockito.verify(observer).onChanged(MovieDetailsViewModel.RepoID(145))

    }

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}