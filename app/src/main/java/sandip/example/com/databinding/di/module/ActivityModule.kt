package sandip.example.com.databinding.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sandip.example.com.databinding.MainActivity

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    abstract fun contributeStorageActivity(): MainActivity

}