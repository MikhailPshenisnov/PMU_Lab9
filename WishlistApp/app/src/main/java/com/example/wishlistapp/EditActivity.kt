package com.example.wishlistapp

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.wishlistapp.api.models.ItemModel
import com.example.wishlistapp.ui.state.UiState
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    private val itemsViewModel: ItemsViewModel by viewModels()

    private lateinit var wishlistContainer: LinearLayout
    private lateinit var statusLabel: TextView

    private lateinit var userId: String
    private lateinit var userLogin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        userId = intent.getStringExtra("USER_ID") ?: run {
            Toast.makeText(
                this,
                "Authorization needed",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            return
        }

        userLogin = intent.getStringExtra("USER_LOGIN") ?: ""

        wishlistContainer = findViewById(R.id.wishlist_container)
        statusLabel = findViewById(R.id.user_wishlist_status_label)

        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.share_button).setOnClickListener {
            copyToClipboard(userId)
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.add_button).setOnClickListener {
            showCreateItemDialog()
        }

        observeViewModel()

        itemsViewModel.loadUserItems(userId)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            itemsViewModel.itemsState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        statusLabel.text = "Loading..."
                    }

                    is UiState.Success -> {
                        renderItems(state.data)
                    }

                    is UiState.Error -> {
                        statusLabel.text = state.message
                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            itemsViewModel.actionState.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        itemsViewModel.loadItems()
                    }

                    is UiState.Error -> {
                        Toast.makeText(
                            this@EditActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun renderItems(items: List<ItemModel>) {
        wishlistContainer.removeAllViews()

        if (items.isEmpty()) {
            statusLabel.text = "List is empty"
            return
        }

        statusLabel.text = "My wishlist"

        items.forEach { item ->
            wishlistContainer.addView(createItemCard(item))
        }
    }

    private fun createItemCard(item: ItemModel): LinearLayout {
        val view = layoutInflater.inflate(
            R.layout.editable_item_card,
            wishlistContainer,
            false
        ) as LinearLayout

        view.findViewById<TextView>(R.id.item_title).text = item.title
        view.findViewById<TextView>(R.id.item_price).text = item.price.toString()

        view.findViewById<Button>(R.id.link_button).setOnClickListener {
            copyToClipboard(item.link.toString())
        }

        view.findViewById<Button>(R.id.edit_button).setOnClickListener {
            showUpdateItemDialog(item)
        }

        view.findViewById<Button>(R.id.delete_button).setOnClickListener {
            itemsViewModel.deleteItem(item.id)
        }

        return view
    }

    private fun showCreateItemDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.create_item_dialog)

        val titleInput = dialog.findViewById<EditText>(R.id.item_title_input)
        val priceInput = dialog.findViewById<EditText>(R.id.item_price_input)
        val linkInput = dialog.findViewById<EditText>(R.id.item_link_input)

        dialog.findViewById<Button>(R.id.create_item_input_button).setOnClickListener {
            itemsViewModel.createItem(
                userId = userId,
                title = titleInput.text.toString(),
                price = priceInput.text.toString().toDoubleOrNull() ?: 0.0,
                link = linkInput.text.toString()
            )
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.close_dialog_button).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showUpdateItemDialog(item: ItemModel) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.edit_item_dialog)

        val titleInput = dialog.findViewById<EditText>(R.id.item_title_input)
        val priceInput = dialog.findViewById<EditText>(R.id.item_price_input)
        val linkInput = dialog.findViewById<EditText>(R.id.item_link_input)

        titleInput.setText(item.title)
        priceInput.setText(item.price.toString())
        linkInput.setText(item.link)

        dialog.findViewById<Button>(R.id.update_item_input_button).setOnClickListener {
            itemsViewModel.updateItem(
                itemId = item.id,
                userId = userId,
                title = titleInput.text.toString(),
                price = priceInput.text.toString().toDoubleOrNull() ?: 0.0,
                link = linkInput.text.toString()
            )
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.close_dialog_button).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun copyToClipboard(text: String) {
        val clipboard =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("text", text))
    }
}
