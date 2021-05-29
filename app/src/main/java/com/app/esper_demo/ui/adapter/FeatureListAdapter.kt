package com.app.esper_demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.esper_demo.EsperApplication
import com.app.esper_demo.R
import com.app.esper_demo.databinding.FeatureAdapterItemBinding
import com.app.esper_demo.utils.ImageUtils
import javax.inject.Inject

class FeatureListAdapter(private val features: List<FeatureListAdapterItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Inject
    lateinit var imageUtils: ImageUtils

    private var itemSelectedListener: ItemSelectedListener? = null

    init {
        EsperApplication.getAppComponent().inject(this)
    }

    fun setItemSelectedListener(itemSelectedListener: ItemSelectedListener?) {
        this.itemSelectedListener = itemSelectedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.feature_adapter_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as ItemViewHolder
        if (itemSelectedListener != null) {
            itemHolder.binding.checkAdapterItem.setOnCheckedChangeListener { _, isChecked ->
                if (features[position].isChecked != isChecked) {
                    features[position].isChecked = isChecked
                    itemSelectedListener?.onItemSelect(
                        features[position].featureId!!,
                        features[position].optionId!!,
                        isChecked
                    )
                }
            }
        }
        itemHolder.bind(features[position])
    }

    override fun getItemCount(): Int {
        return features.size
    }

    inner class ItemViewHolder(val binding: FeatureAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FeatureListAdapterItem) {
            if (item.showTitle) {
                binding.txtItemTitle.visibility = View.VISIBLE
                binding.txtItemTitle.text = item.title

                binding.txtAdapterItem.visibility = View.GONE
                binding.imgAdapterItem.visibility = View.GONE
                binding.checkAdapterItem.visibility = View.GONE
            } else {
                binding.txtItemTitle.visibility = View.GONE

                binding.txtAdapterItem.visibility = View.VISIBLE
                binding.imgAdapterItem.visibility = View.VISIBLE
                binding.checkAdapterItem.visibility = View.VISIBLE
                binding.txtAdapterItem.text = item.name
                imageUtils.loadImageWithUrl(item.logo ?: "", binding.imgAdapterItem, -1, -1)
            }
            binding.checkAdapterItem.isChecked = item.isChecked
        }
    }

    interface ItemSelectedListener {
        fun onItemSelect(featureId: String, optionId: String, isChecked: Boolean)
    }
}