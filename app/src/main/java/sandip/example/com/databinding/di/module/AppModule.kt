package sandip.example.com.databinding.di.module

import android.annotation.SuppressLint
import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sandip.example.com.databinding.data.AppDao
import sandip.example.com.databinding.database.AppDatabase
import sandip.example.com.databinding.remote.WebServices
import sandip.example.com.databinding.repo.AppRepository
import sandip.example.com.databinding.utils.authUtils.TLSSocketFactory
import sandip.example.com.databinding.utils.authUtils.WebServiceHolder
import sandip.example.com.databinding.utils.helperUtils.AppExecutors
import sandip.example.com.databinding.utils.remoteUtils.LiveDataCallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class AppModule {

    // database injection
    @Provides
    @Singleton
    fun provideDatabaseModule(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "moviesRoomDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun webServiceHolderModule(): WebServiceHolder {
        return WebServiceHolder.instance
    }

    @Provides
    fun provideGsonModule(): Gson = GsonBuilder().create()

    @Provides
    fun provideExecutorModule(): AppExecutors = AppExecutors()

    @Provides
    @Singleton
    fun provideGithubDao(database: AppDatabase) = database.appDao()


    @Provides
    @Singleton
    fun provideAppRepository(webservice: WebServices, executor: AppExecutors, dao: AppDao) =
        AppRepository(webservice, executor, dao)


    @SuppressLint("NewApi")
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .sslSocketFactory(TLSSocketFactory())
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        okHttpClient.sslSocketFactory()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiWebservice(restAdapter: Retrofit): WebServices {
        val webService = restAdapter.create(WebServices::class.java)
        WebServiceHolder.instance.setAPIService(webService)
        return webService
    }
}