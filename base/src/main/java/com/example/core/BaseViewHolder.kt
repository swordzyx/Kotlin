package com.example.core

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.IdRes
import android.widget.TextView
import java.util.HashMap

abstract class BaseViewHolder(itemView: View) : ViewHolder(itemView) {
    @SuppressLint("UseSparseArrays")
    private val viewHashMap: MutableMap<Int, View?> = HashMap()
    protected fun <T : View?> getView(@IdRes id: Int): T {
        var view = viewHashMap[id]
        if (view == null) {
            view = itemView.findViewById(id)
            viewHashMap[id] = view
        }
        return view as T
    }

    protected fun setText(@IdRes id: Int, text: String?) {
        getView<TextView>(id)?.text = text
    }
}