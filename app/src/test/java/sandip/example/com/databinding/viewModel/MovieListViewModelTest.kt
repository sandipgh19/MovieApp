package sandip.example.com.databinding.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*
import sandip.example.com.databinding.objects.MovieListItem
import sandip.example.com.databinding.repo.AppRepository
import sandip.example.com.databinding.utils.remoteUtils.Resource

@RunWith(JUnit4::class)
class MovieListViewModelTest {

    @Rule
    @JvmField
    val intantExecutor = InstantTaskExecutorRule()

    val repo = mock(AppRepository::class.java)
    val repoViewModel = MovieListViewModel(repo = repo)

    @Test
    fun testNull() {
        assertThat(repoViewModel.result, notNullValue())
        verify(repo, never()).fetchMovieList()
    }

    @Test
    fun dontFetchWithoutObserver() {
        repoViewModel.init(refID = "foo")
        verify(repo, never()).fetchMovieList()
    }

    @Test
    fun fetchWithObserver() {
        repoViewModel.init("foo")
        repoViewModel.result.observeForever(mock())
        verify(repo).fetchMovieList()
    }

    @Test
    fun fetchWhenObserve() {
        repoViewModel.result.observeForever(mock())
        repoViewModel.init("foo")
        verify(repo).fetchMovieList()
    }

    @Test
    fun blankRefID() {
        repoViewModel.init("")
        val observer = mock<Observer<Resource<List<MovieListItem>>>>()
        repoViewModel.result.observeForever(observer)
        verify(observer).onChanged(null)
    }

    @Test
    fun resetSameQuery() {
        val observer = mock<Observer<MovieListViewModel.RepoID>>()
        repoViewModel.refID.observeForever(observer)
        verifyNoMoreInteractions(observer)
        repoViewModel.init("foo")
        verify(observer).onChanged(MovieListViewModel.RepoID("foo"))
        reset(observer)
        repoViewModel.init("foo")
        verifyNoMoreInteractions(observer)
        repoViewModel.init("foo1")
        verify(observer).onChanged(MovieListViewModel.RepoID("foo1"))

    }

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

}