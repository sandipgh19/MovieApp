package sandip.example.com.databinding.testData

import sandip.example.com.databinding.objects.MovieDetailsResponse
import sandip.example.com.databinding.objects.MovieListItem

class TestMovieData {

    fun prepareVideoListItem(
        id: Int,
        title: String?,
        overView: String?,
        posterPath: String?,
        releaseDate: String?
    ): MovieListItem {
        return MovieListItem(
            id = id,
            title = title,
            overview = overView,
            posterPath = posterPath,
            releaseDate = releaseDate
        )
    }

    fun prepareVideoDetails(
        id: Int,
        title: String?,
        overView: String?,
        posterPath: String?,
        releaseDate: String?
    ): MovieDetailsResponse {
        return MovieDetailsResponse(
            id = id,
            title = title,
            overview = overView,
            posterPath = posterPath,
            releaseDate = releaseDate
        )
    }
}