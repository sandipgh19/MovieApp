package sandip.example.com.databinding.binding

import android.databinding.BindingAdapter
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


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
        Glide.with(fragment).load(url).into(imageView)
    }

    @BindingAdapter("upperString")
    fun bindText(textView:TextView, value: String?){
        textView.text = value?.toUpperCase()
    }

    @BindingAdapter(value = ["timeStamp", "format", "emptyTxt"], requireAll = true)
    fun bindDateTime(textView: TextView, timeStamp: String?, format: String, emptyTxt: String) {
        if (timeStamp != null) {
            val format1 = SimpleDateFormat(
                "YYYY-MM-DD", Locale.US
            )
            format1.timeZone = TimeZone.getTimeZone("UTC")
            val mDate = format1.parse(timeStamp)
            val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
            textView.text = dateFormat.format(mDate)
        } else
            textView.text = emptyTxt

    }

}