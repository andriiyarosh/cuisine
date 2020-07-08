package com.work.cuisine.presentation.receipts.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.cuisine.R
import com.work.cuisine.network.moodels.instructions.InstructionStep
import kotlinx.android.synthetic.main.item_instruciton_step.view.*

class InstructionListAdapter : RecyclerView.Adapter<InstructionListAdapter.ViewHolder>() {

    private var instructions = listOf<InstructionStep>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_instruciton_step, parent, false)
    )

    override fun getItemCount() = instructions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            stepNumber.text = (position + 1).toString()
            step.text = instructions[position].step
        }
    }

    fun setItems(instructions: List<InstructionStep>) {
        this.instructions = instructions
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}