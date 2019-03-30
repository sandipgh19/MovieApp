package sandip.example.com.databinding.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast

import sandip.example.com.databinding.R
import sandip.example.com.databinding.adapter.MovieListAdapter
import sandip.example.com.databinding.binding.FragmentDataBindingComponent
import sandip.example.com.databinding.databinding.FragmentMovieListBinding
import sandip.example.com.databinding.di.Injectable
import sandip.example.com.databinding.utils.helperUtils.AppExecutors
import sandip.example.com.databinding.utils.helperUtils.autoCleared
import sandip.example.com.databinding.utils.remoteUtils.Status
import sandip.example.com.databinding.viewModel.MovieListViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieListFragment : Fragment(), Injectable {

    var binding by autoCleared<FragmentMovieListBinding>()
    var adapter by autoCleared<MovieListAdapter>()

    var databindingComponents : DataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var executors: AppExecutors

    private lateinit var viewModel: MovieListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_movie_list,
            container,
            false,
            databindingComponents)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieListViewModel::class.java)

        adapter = MovieListAdapter(dataBindingComponent = databindingComponents, appExecutors = executors) {item->


        }

        binding.let {
            it.adapter = adapter
            it.lifecycleOwner = this
        }

        viewModel.init(refID = "foo")
        initVideoList(viewModel = viewModel)


    }

    private fun initVideoList(viewModel: MovieListViewModel) {
        viewModel.result.observe(this, Observer { listResource ->
            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            binding.resource = listResource
            binding.count = listResource?.data?.size
            adapter.submitList(listResource?.data)
            endProgress()
            when (listResource?.status) {
                Status.SUCCESS -> {
                    endProgress()
                }

                Status.ERROR -> {
                    endProgress()
                    Toast.makeText(requireContext(), getString(R.string.generalError), Toast.LENGTH_LONG).show()
                }

                Status.LOADING ->{startProgress()}
            }
        })
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


}