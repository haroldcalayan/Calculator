package com.haroldcalayan.calculator.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.haroldcalayan.calculator.BR
import com.haroldcalayan.calculator.R
import com.haroldcalayan.calculator.databinding.AdapterButtonItemBinding

class ButtonAdapter(private var data: Array<String>, private val listener: ButtonAdapterListener) : RecyclerView.Adapter<ButtonAdapter.ButtonAdapterViewHolder>() {

    interface ButtonAdapterListener {
        fun onButtonItemClick(position: Int, text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonAdapterViewHolder {
        val binding: AdapterButtonItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_button_item,
            parent,
            false
        )
        return ButtonAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ButtonAdapterViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.buttonText.setOnClickListener { listener.onButtonItemClick(position, data[position]) }
    }

    override fun getItemCount() = data.size

    fun updateData(data: Array<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ButtonAdapterViewHolder : RecyclerView.ViewHolder {

        val binding: AdapterButtonItemBinding

        constructor(binding: AdapterButtonItemBinding) : super(binding.root) {
            this.binding = binding
        }

        fun bind(text: String) {
            binding.setVariable(BR.buttonText, text)
            binding.executePendingBindings()
        }
    }
}