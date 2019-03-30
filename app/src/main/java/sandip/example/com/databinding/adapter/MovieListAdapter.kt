package sandip.example.com.databinding.adapter

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import sandip.example.com.databinding.R
import sandip.example.com.databinding.databinding.LayoutMovieListItemBinding
import sandip.example.com.databinding.objects.MovieListItem
import sandip.example.com.databinding.utils.DataBoundListAdapter
import sandip.example.com.databinding.utils.helperUtils.AppExecutors

class MovieListAdapter (
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((MovieListItem) -> Unit)?
) : DataBoundListAdapter<MovieListItem, LayoutMovieListItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<MovieListItem>() {
        override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {

    override fun createBinding(parent: ViewGroup): LayoutMovieListItemBinding {
        val binding = DataBindingUtil
            .inflate<LayoutMovieListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.layout_movie_list_item,
                parent,
                false,
                dataBindingComponent
            )
        binding.root.setOnClickListener {
            binding.item?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: LayoutMovieListItemBinding, item: MovieListItem) {
        binding.item = item
    }
}