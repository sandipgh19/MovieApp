package sandip.example.com.databinding.repo

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import sandip.example.com.databinding.data.AppDao
import sandip.example.com.databinding.database.AppDatabase
import sandip.example.com.databinding.remote.WebServices
import sandip.example.com.databinding.util.InstantAppExecutors

@RunWith(JUnit4::class)
class AppRepositoryTest {

    private lateinit var repository: AppRepository

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()

    private var webService = Mockito.mock(WebServices::class.java)
    private var dao = Mockito.mock(AppDao::class.java)

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)
        Mockito.`when`(db.appDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = AppRepository(webservice = webService, executor = InstantAppExecutors(), dao = dao)
    }

}