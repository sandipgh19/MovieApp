package sandip.example.com.databinding.binding

import android.databinding.BindingAdapter
import android.support.v4.app.Fragment
import android.support.v4.widget.CircularProgressDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import sandip.example.com.databinding.objects.MovieGenres
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * Binding adapters that work with a fragment instance.
 */
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment)  {

    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        val originalUrl = "https://image.tmdb.org/t/p/w500$url"
        val circularProgressDrawable = CircularProgressDrawable(fragment.requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Log.e("ImageUrl", "Url $originalUrl")
        Glide.with(fragment).load(originalUrl).placeholder(circularProgressDrawable).into(imageView)
    }

    @BindingAdapter("upperString")
    fun bindText(textView:TextView, value: String?){
        textView.text = value?.toUpperCase()
    }

    @BindingAdapter(value = ["timeStamp", "format", "emptyTxt"], requireAll = true)
    fun bindDateTime(textView: TextView, timeStamp: String?, format: String, emptyTxt: String) {
        if (timeStamp != null) {
            val format1 = SimpleDateFormat(
                "yyyy-MM-DD", Locale.US
            )
            format1.timeZone = TimeZone.getTimeZone("UTC")
            val mDate = format1.parse(timeStamp)
            val calendar = Calendar.getInstance()
            calendar.time = mDate
            val dayNumberSuffix = getDayNumberSuffix(calendar.get(Calendar.DAY_OF_MONTH))

            val dateFormat = SimpleDateFormat(format.replace("dd","dd'${dayNumberSuffix}'"), Locale.ENGLISH)
            textView.text = dateFormat.format(mDate)
        } else
            textView.text = emptyTxt

    }

    private fun getDayNumberSuffix(day: Int): String {
        if (day in 11..13) {
            return "th"
        }
        when (day % 10) {
            1 -> return "st"
            2 -> return "nd"
            3 -> return "rd"
            else -> return "th"
        }
    }

    @BindingAdapter(value = ["genresList"], requireAll = true)
    fun bindGenres(textView: TextView, list: MutableList<MovieGenres?>?){
        list?.let {
            val localList: MutableList<String?> = ArrayList()
            it.filterIndexed { index, movieGenres ->
                localList.add(movieGenres?.name)
            }
            textView.text = localList.toString().replace("[","").replace("]","")
        }
    }

}