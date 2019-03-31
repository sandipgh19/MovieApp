package sandip.example.com.databinding.objects

import com.google.gson.annotations.SerializedName

data class MovieGenres(

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("name")
    var name: String? = null

)