package com.tjohnn.routinechecks.ui.routines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.databinding.ListItemRoutineBinding

class RoutineAdapter (
    private val onItemClickListener: (Routine) -> Unit
) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    var items = listOf<Routine>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = ListItemRoutineBinding.inflate(LayoutInflater.from(parent.context))
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
            binding.root.setOnClickListener {
                onItemClickListener(routine)
            }
        }
    }
}