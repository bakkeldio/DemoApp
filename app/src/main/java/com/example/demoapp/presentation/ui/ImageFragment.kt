package com.example.demoapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.demoapp.R
import com.example.demoapp.databinding.ImagesFragmentBinding
import com.example.demoapp.presentation.adapter.ImagesAdapter
import com.example.demoapp.presentation.adapter.LoadStateAdapter
import com.example.demoapp.presentation.viewmodel.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImageFragment : Fragment(R.layout.images_fragment) {

    private val viewModel by viewModels<ImagesViewModel>()

    private var _binding: ImagesFragmentBinding? = null
    val adapter = ImagesAdapter()

    private val binding :ImagesFragmentBinding
    get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImagesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvImages.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter {
            adapter.retry()
        },
        footer = LoadStateAdapter{
            adapter.retry()
        })

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener {loadState->
            binding.rvImages.isVisible = loadState.source.refresh is LoadState.NotLoading

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.liveData.observe(viewLifecycleOwner, {
            Log.d("Items", it.toString())
            lifecycleScope.launchWhenStarted {
                adapter.submitData(it)
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}