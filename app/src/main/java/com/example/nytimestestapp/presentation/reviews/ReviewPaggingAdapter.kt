package com.example.nytimestestapp.presentation.reviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimestestapp.R
import com.example.nytimestestapp.domain.reviews.ReviewItem

class ReviewPaggingAdapter(context: Context) : PagingDataAdapter<ReviewItem, ReviewViewHolder>(ReviewDiffItemCallback)  {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(layoutInflater.inflate(R.layout.review_item, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }
}

class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvTitle = itemView.findViewById<TextView>(R.id.tvReviewTitle)
    val tvSummary = itemView.findViewById<TextView>(R.id.tvSummaryShort)
    val tvDate = itemView.findViewById<TextView>(R.id.tvPublicationDate)

    fun bind(item:ReviewItem){
        tvTitle.text = item.headLine
        tvSummary.text=item.summaryShort
        tvDate.text=item.publicationDate
    }

}

private object ReviewDiffItemCallback : DiffUtil.ItemCallback<ReviewItem>() {

    override fun areItemsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
        return oldItem == newItem
    }
}