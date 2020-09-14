package com.example.a07_02_shoppinglist_db

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.shoping_item.view.*

class ShoppingItemAdapter(context: Context, private val items: MutableList<ShoppingItem>) :
    ArrayAdapter<ShoppingItem>(context, 0, items) {

    data class ViewHolder(val name:TextView, val quantity: TextView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var viewHolder: ViewHolder
        lateinit var view: View

        var builder = AlertDialog.Builder(context)
        builder.setTitle("Really delete").setMessage("Approve Your choise!")
            .setPositiveButton("Delete"){
                    dialog, id -> items.removeAt(position)
                notifyDataSetChanged()
            }
            .setNegativeButton("Don't do it!") { _, _ -> }
            .setNeutralButton("let me think...") { _, _ -> }
        var dialog = builder.create()

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.shoping_item, parent, false)
            viewHolder = ViewHolder(view.item_name, view.item_count)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }
        val item = getItem(position)!!

        viewHolder.name.text = item.name
        viewHolder.quantity.text = context.resources
            .getString(R.string.quantity_text, item.quantity, item.unit)

        view.setOnClickListener {
            Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
        }
        // Listener - delete item
        view.item_button.setOnClickListener {
            dialog.show()
            Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
        }
        return view
    }
}
