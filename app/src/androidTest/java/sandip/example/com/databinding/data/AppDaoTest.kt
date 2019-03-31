package sandip.example.com.databinding.data

import android.support.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import sandip.example.com.databinding.testData.TestMovieData
import sandip.example.com.databinding.utils.LiveDataTestUtil.getValue

@RunWith(AndroidJUnit4::class)
class AppDaoTest: DbTest() {

//    @Test
//    fun deleteAndInsertMovieList() {
//        val movieListItem = TestMovieData().prepareVideoListItem(id = 1, title = "Title", overView = "Overview", posterPath = "/dghsghs.jpg", releaseDate = "24 Jun 2015")
//        db.appDao().deleteAndInsertMovieList(listItem = listOf(movieListItem))
//
//        val value = getValue(db.appDao().fetchMovieList())
//        assertThat(value, notNullValue())
//        assertThat(value.size, `is`(1))
//
//    }

}