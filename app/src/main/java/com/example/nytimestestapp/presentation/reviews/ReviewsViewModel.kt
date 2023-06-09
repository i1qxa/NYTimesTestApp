package com.example.nytimestestapp.presentation.reviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nytimestestapp.domain.reviews.ReviewItem
import com.example.nytimestestapp.domain.reviews.ReviewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    val reviewsFlow = reviewsRepository.getReviewsList(null).cachedIn(viewModelScope)

    val tempLD = MutableLiveData<PagingData<ReviewItem>>()

    init {
        viewModelScope.launch {
            reviewsRepository.getReviewsList(null).collect() {
                tempLD.postValue(it)
            }
        }
    }
}
