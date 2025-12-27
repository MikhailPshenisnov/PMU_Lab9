package com.example.wishlistapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wishlistapp.api.models.ItemModel

class WishlistAdapter(
    private val context: Context,
    private val items: List<ItemModel>
) : RecyclerView.Adapter<WishlistAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.item_title)
        val price = view.findViewById<TextView>(R.id.item_price)
        val linkButton = view.findViewById<Button>(R.id.link_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title ?: "No Title"
        holder.price.text = "Price: ${item.price}"

        holder.linkButton.setOnClickListener {
            val link = item.link
            if (!link.isNullOrEmpty()) {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("Item Link", link))
                Toast.makeText(context, "Link copied!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No link available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
