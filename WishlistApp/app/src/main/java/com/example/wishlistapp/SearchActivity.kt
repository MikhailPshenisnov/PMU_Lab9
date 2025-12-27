package com.example.wishlistapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.wishlistapp.api.models.ItemModel
import com.example.wishlistapp.ui.state.UiState

class SearchActivity : AppCompatActivity() {

    private lateinit var searchInput: EditText
    private lateinit var searchButton: Button
    private lateinit var statusLabel: TextView
    private lateinit var wishlistContainer: LinearLayout
    private lateinit var backButton: Button

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchInput = findViewById(R.id.search_code_input)
        searchButton = findViewById(R.id.search_code_button)
        statusLabel = findViewById(R.id.search_status_label)
        wishlistContainer = findViewById(R.id.wishlist_container)
        backButton = findViewById(R.id.back_button)

        backButton.setOnClickListener { finish() }

        searchButton.setOnClickListener {
            val code = searchInput.text.toString()
            if (code.isNotEmpty()) {
                viewModel.searchItems(code)
            } else {
                Toast.makeText(
                    this,
                    "Enter user code",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        observeItems()
    }

    private fun observeItems() {
        lifecycleScope.launchWhenStarted {
            viewModel.itemsState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        statusLabel.text = "Loading..."
                        wishlistContainer.removeAllViews()
                    }

                    is UiState.Success -> {
                        val items = state.data
                        statusLabel.text = "Found ${items.size} items"
                        displayItems(items)
                    }

                    is UiState.Error -> {
                        statusLabel.text = "Error: ${state.message}"
                        wishlistContainer.removeAllViews()
                        Toast.makeText(
                            this@SearchActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun displayItems(items: List<ItemModel>) {
        wishlistContainer.removeAllViews()
        val adapter = WishlistAdapter(this, items)

        for (i in items.indices) {
            val holder = adapter.onCreateViewHolder(wishlistContainer, 0)
            adapter.onBindViewHolder(holder, i)
            wishlistContainer.addView(holder.itemView)
        }
    }
}
