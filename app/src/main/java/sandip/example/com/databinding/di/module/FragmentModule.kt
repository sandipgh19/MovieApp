package sandip.example.com.databinding.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sandip.example.com.databinding.fragments.MovieDetailsFragment
import sandip.example.com.databinding.fragments.MovieListFragment

@Suppress("unused")
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment

}