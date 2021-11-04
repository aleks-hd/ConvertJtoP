package com.hfad.convertjtop.model

import android.view.View

interface IListFile<V:IItemView>  {
   // var itemClickListener:((V)->Unit)?
  // fun itemClock(name: String)
    fun bindView(view:V)
    fun getCount():Int
}