package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodapp.databinding.CategoryRowLayoutBinding
import com.example.foodapp.model.Category

class RecyclerViewAdapter(private val categoryList: List<Category>, val listener: Listener) :
    RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(category: Category)
    }

    //lateinit var binding: RowLayoutBinding

    inner class RowHolder(private val itemBinding: CategoryRowLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: Category) {
            //itemBinding.idCategory.text = category.idCategory
            itemBinding.strCategory.text = category.strCategory

            Glide
                .with(itemBinding.root.context)
                .load(category.strCategoryThumb)
                //.centerCrop()
                .apply(RequestOptions().override(1500,150))
                .into(itemBinding.imageView)

            itemBinding.imageView.setOnClickListener {
                listener.onItemClick(category)
            }

            //itemBinding.strCategoryDescription.text = category.strCategoryDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemBinding = CategoryRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return categoryList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(categoryList[position])
    }
}