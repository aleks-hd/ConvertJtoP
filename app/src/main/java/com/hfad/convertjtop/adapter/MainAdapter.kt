package com.hfad.convertjtop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.convertjtop.databinding.ItemBinding
import com.hfad.convertjtop.model.IItemView
import com.hfad.convertjtop.model.IListFilePresentor

class MainAdapter(val presenter: IListFilePresentor) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))



    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position }
        )

    inner class ViewHolder(val vb: ItemBinding) :
        RecyclerView.ViewHolder(vb.root), IItemView {

        override var pos: Int = -1
        override fun setName(name: String) =with(vb){
            nameFile.text =name
        vb?.btnConvert.setOnClickListener {
            it -> presenter.itemClock(name)
        }
        }

    }

    override fun getItemCount() = presenter.getCount()
}

