package sandip.example.com.databinding.fragments


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import sandip.example.com.databinding.R
import sandip.example.com.databinding.binding.FragmentDataBindingComponent
import sandip.example.com.databinding.di.Injectable
import sandip.example.com.databinding.utils.helperUtils.autoCleared
import sandip.example.com.databinding.viewModel.MovieDetailsViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieDetailsFragment : Fragment(), Injectable {

    private val MOVIE_ID = "movie_id"
    var binding by autoCleared<FragmentMovieDetailsBinding>()

    private lateinit var viewModel: MovieDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MOVIE_ID, entryId)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieDetailsViewModel::class.java)
        entryId = savedInstanceState?.getInt(MOVIE_ID) ?: MovieDetailsFragmentArgs.fromBundle(arguments).entryID
        viewModel.init(refID = id)

    }


        companion object {
        var entryId: Int = -1
    }

}
