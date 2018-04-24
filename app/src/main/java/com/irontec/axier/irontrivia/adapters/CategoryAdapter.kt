package com.irontec.axier.irontrivia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.irontec.axier.irontrivia.R
import com.irontec.roomexample.database.entities.DBTriviaCategories
import kotlinx.android.synthetic.main.row_category.view.*

class CategoryAdapter(private val mContext: Context, private val mData: MutableList<DBTriviaCategories>) : BaseAdapter() {

    fun addAll(categories: List<DBTriviaCategories>) {
        mData.clear()
        mData.addAll(categories)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): Any {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder
        if (view != null) {
            viewHolder = view.tag as ViewHolder
        } else {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.row_category, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }

        val category = mData[position]
        viewHolder.name.text = category.name

        return view!!
    }

    internal class ViewHolder(view: View) {

        var name: TextView

        init {
            name = view.category_name
        }
    }

}