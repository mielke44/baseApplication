package br.com.wilson.mielke.baseapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.wilson.mielke.baseapplication.R
import br.com.wilson.mielke.baseapplication.config.models.EventModel
import br.com.wilson.mielke.baseapplication.databinding.EventAdapterItemBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter(private val eventList: List<EventModel>, private val listener: (EventModel) -> Unit): RecyclerView.Adapter<EventListAdapter.EventAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapterViewHolder {
        return EventAdapterViewHolder(
            EventAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: EventAdapterViewHolder, position: Int) {
        val currentItem = eventList[position]
        holder.bindView(currentItem)
    }

    override fun getItemCount(): Int = eventList.size

    inner class EventAdapterViewHolder(private val binding: EventAdapterItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(model: EventModel){
            binding.eventContainer.setOnClickListener { listener.invoke(model) }
            binding.eventTitle.text = model.title
            binding.eventDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date(model.date))
            Glide.with(itemView.context)
                .load(model.image)
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_error)
                .centerCrop()
                .into(binding.eventImage)
        }
    }
}