package sandip.example.com.databinding.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import sandip.example.com.databinding.MainActivity

import sandip.example.com.databinding.R
import sandip.example.com.databinding.binding.FragmentDataBindingComponent
import sandip.example.com.databinding.databinding.FragmentMovieDetailsBinding
import sandip.example.com.databinding.di.Injectable
import sandip.example.com.databinding.utils.helperUtils.autoCleared
import sandip.example.com.databinding.utils.remoteUtils.Status
import sandip.example.com.databinding.viewModel.MovieDetailsViewModel
import javax.inject.Inject
import android.view.*
import sandip.example.com.databinding.helper.ConverterUtils


/**
 * A simple [Fragment] subclass.
 *
 */
class MovieDetailsFragment : Fragment(), Injectable {

    private val MOVIE_ID = "movie_id"
    private val MOVIE_TITLE = "movie_title"


    var binding by autoCleared<FragmentMovieDetailsBinding>()

    private lateinit var viewModel: MovieDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MOVIE_ID, entryId)
        outState.putString(MOVIE_TITLE, movieTitle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
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
        movieTitle = savedInstanceState?.getString(MOVIE_TITLE) ?: MovieDetailsFragmentArgs.fromBundle(arguments).title

        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.let {
            it.title = movieTitle
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.let {
            it.converter = ConverterUtils()
            it.lifecycleOwner = this
        }
        viewModel.init(refID = entryId)

        initVideoDetails(viewModel = viewModel)

    }

    private fun initVideoDetails(viewModel: MovieDetailsViewModel) {
        viewModel.result.observe(this, Observer { listResource ->
            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            binding.resource = listResource
            binding.item = listResource?.data
            if (listResource?.data == null) binding.count = 0
            else binding.count = 1
            endProgress()
            when (listResource?.status) {
                Status.SUCCESS -> {
                    endProgress()
                }

                Status.ERROR -> {
                    endProgress()
                    Toast.makeText(requireContext(), getString(R.string.generalError), Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {
                    startProgress()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        when (item.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startProgress() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun endProgress() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }


    companion object {
        var entryId: Int = -1
        var movieTitle: String? = " "
    }

}
