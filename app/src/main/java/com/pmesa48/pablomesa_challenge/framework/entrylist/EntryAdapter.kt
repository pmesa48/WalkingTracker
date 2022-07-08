package com.pmesa48.pablomesa_challenge.framework.entrylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmesa48.pablomesa_challenge.databinding.ListItemEntryBinding
import com.pmesa48.pablomesa_challenge.framework.common.GlideApp
import com.pmesa48.pablomesa_challenge.model.Entry

class EntryAdapter : ListAdapter<Entry, EntryViewHolder>(EntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EntryViewHolder(
            ListItemEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class EntryViewHolder(private val binding: ListItemEntryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(entry: Entry) {
        GlideApp.with(binding.root)
            .load(entry.imagePath)
            .into(binding.entryImageView)
    }

}

class EntryDiffCallback : DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry) =
        oldItem.date == newItem.date

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry) =
        oldItem == newItem
}
