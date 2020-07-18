package com.tjohnn.routinechecks.ui.routines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.databinding.ListItemRoutineBinding
import com.tjohnn.routinechecks.utils.toFriendlyText

class RoutineAdapter (
    private val onItemClickListener: (Routine) -> Unit
) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    var items = listOf<Routine>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = ListItemRoutineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  RoutineViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bindData(items[position])
    }


    inner class RoutineViewHolder(
        private val binding: ListItemRoutineBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(routine: Routine) {
            binding.titleText.text = routine.title
            binding.descriptionText.text = routine.description

            // display previous check time if routine is still pending
            binding.timeText.text = if(routine.isPending)
                routine.previousCheckTime.toFriendlyText(itemView.context)
            else
                routine.nextCheckTime.toFriendlyText(itemView.context)

            binding.root.setOnClickListener {
                onItemClickListener(routine)
            }
        }
    }
}