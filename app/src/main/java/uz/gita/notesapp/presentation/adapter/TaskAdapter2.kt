package uz.gita.notesapp.presentation.adapter

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.notesapp.R
import uz.gita.notesapp.data.models.TaskData
import uz.gita.notesapp.databinding.ItemTaskBinding
import kotlin.math.abs

class TaskAdapter2() : ListAdapter<TaskData, TaskAdapter2.VH>(object : DiffUtil.ItemCallback<TaskData>() {

    override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem.title == newItem.title && oldItem.id == newItem.id
    }

}) {
    private var itemClickListener: ((TaskData,Int) -> Unit)? = null
    private var removeItemClickListener: ((TaskData,Int) -> Unit)? = null
    private var changeItemClickListener: ((TaskData,Int) -> Unit)? = null
    private var positionC : Int = 0

    inner class VH(var binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (!getItem(absoluteAdapterPosition).isChecked) {
                    itemClickListener?.invoke(getItem(absoluteAdapterPosition),absoluteAdapterPosition)
                }
            }
            binding.btnDelete.setOnClickListener {
                removeItemClickListener?.invoke(getItem(absoluteAdapterPosition),absoluteAdapterPosition)
            }
        }
        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            binding.apply {
                tvTitle.text = item.title
                checkbox.isChecked = item.isChecked
                if (item.isChecked){
                    tvTitle.setPaintFlags(tvTitle.getPaintFlags() or STRIKE_THRU_TEXT_FLAG)
                } else{
                    tvTitle.setPaintFlags(tvTitle.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                }
                checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked){
                        tvTitle.setPaintFlags(tvTitle.getPaintFlags() or STRIKE_THRU_TEXT_FLAG)
                        item.isChecked = true
                    }
                    else{
                        tvTitle.setPaintFlags(tvTitle.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                        item.isChecked = false
                    }
                    changeItemClickListener?.invoke(item,absoluteAdapterPosition)
                }
            }
        }
        fun changeText(text :String){
            binding.tvTitle.setText(text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        return holder.bind()
    }

    fun setItemClickListener(block: ((TaskData,Int) -> Unit)) {
        itemClickListener = block
    }
    fun setRemoveItemClickListener(block: ((TaskData, Int) -> Unit)) {
        removeItemClickListener = block
    }

    fun setChangeItemClickListener(block: ((TaskData, Int) -> Unit)) {
        changeItemClickListener = block
    }
}