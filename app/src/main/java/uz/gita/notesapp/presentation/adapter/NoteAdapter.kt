package uz.gita.notesapp.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.databinding.ItemNoteBinding
import uz.gita.notesapp.utils.convertLongToTime
import uz.gita.notesapp.utils.convertLongToTime2
import uz.gita.notesapp.utils.convertToDate

class NoteAdapter() : ListAdapter<NoteData, NoteAdapter.VH>(object : DiffUtil.ItemCallback<NoteData>() {

        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem == newItem
        }

    }) {
    private var itemClickListener: ((NoteData) -> Unit)? = null
    private var deleteItemClickListener: ((NoteData,Int) -> Unit)? = null

    inner class VH(var binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.container.setOnClickListener {
                itemClickListener?.invoke(getItem(absoluteAdapterPosition))
            }
            binding.btnDelete.setOnClickListener {
                deleteItemClickListener?.invoke(getItem(absoluteAdapterPosition),absoluteAdapterPosition)
            }
        }
        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            binding.tvTitle.text = item.title
            Log.d("TTT","bind = ${item.description}")
            binding.tvDescription.fromHtml(item.description)

            /**
             * Format create_time to date
             * */
            binding.tvDate.text = item.created_time.toString()
            binding.tvDate.text = convertLongToTime2(item.created_time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        return holder.bind()
    }

    fun ItemClickListener(block: ((NoteData) -> Unit)) {
        itemClickListener = block
    }

    fun DeleteItemListener(block: ((NoteData,Int) -> Unit)) {
        deleteItemClickListener = block
    }
}