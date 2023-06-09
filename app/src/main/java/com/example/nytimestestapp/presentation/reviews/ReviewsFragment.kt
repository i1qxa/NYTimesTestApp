package com.example.nytimestestapp.presentation.reviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytimestestapp.databinding.FragmentReviewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : Fragment() {

    private val viewModel by viewModels<ReviewsViewModel>()
    private val binding by lazy { FragmentReviewsBinding.inflate(layoutInflater) }
    private val adapter by lazy { ReviewPaggingAdapter(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReviewsList.adapter = adapter
        binding.rvReviewsList.layoutManager = LinearLayoutManager(requireContext())
        observeViewModel()
    }

    private fun observeViewModel(){

//        lifecycleScope.launch {
//            viewModel.reviewsFlow.collect() {
//                it.map {
//                    var a = it
//                }
//                adapter.submitData(it)
//            }
//        }

        viewModel.tempLD.observe(viewLifecycleOwner){
            it.map {
                Log.i("FCK", it.headLine.toString())
            }
        }
    }

}