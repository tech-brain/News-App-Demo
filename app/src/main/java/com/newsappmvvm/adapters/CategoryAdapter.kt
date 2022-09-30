package com.newsappmvvm.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsappmvvm.R
import com.newsappmvvm.databinding.ItemCategoryBinding
import com.newsappmvvm.model.CategoryModel

class CategoryAdapter(private val categoryList : ArrayList<CategoryModel>, private val categoryAdapterClick : CategoryAdapterClick):RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
            return categoryList.size
    }
    inner class ViewHolder(private val binding : ItemCategoryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data : CategoryModel){
            binding.tvCategory.text = data.category

            if(data.selected == true){
                binding.clMain.background = ContextCompat.getDrawable(binding.root.context, R.drawable.bg_selected_category)
                binding.tvCategory.setTextColor(ContextCompat.getColorStateList(binding.root.context, R.color.white))
            }else{
                binding.clMain.background = ContextCompat.getDrawable(binding.root.context, R.drawable.bg_unselected_category)
                binding.tvCategory.setTextColor(ContextCompat.getColorStateList(binding.root.context, R.color.black))
            }

            binding.root.setOnClickListener {
                categoryAdapterClick.onChangeCat(data, adapterPosition)
            }
        }
    }

    interface CategoryAdapterClick{
        fun onChangeCat(data: CategoryModel, position: Int)
    }
}