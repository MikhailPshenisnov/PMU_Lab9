package com.example.wishlistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.api.models.ItemModel
import com.example.wishlistapp.repository.ItemsRepository
import com.example.wishlistapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemsViewModel : ViewModel() {

    private val repository = ItemsRepository()

    private val _itemsState =
        MutableStateFlow<UiState<List<ItemModel>>>(UiState.Idle)
    val itemsState: StateFlow<UiState<List<ItemModel>>> = _itemsState

    private val _itemState =
        MutableStateFlow<UiState<ItemModel>>(UiState.Idle)
    val itemState: StateFlow<UiState<ItemModel>> = _itemState

    private val _actionState =
        MutableStateFlow<UiState<String>>(UiState.Idle)
    val actionState: StateFlow<UiState<String>> = _actionState

    fun loadItems() {
        viewModelScope.launch {
            _itemsState.value = UiState.Loading

            val response = repository.getAllItems()

            if (response.isSuccessful) {
                _itemsState.value =
                    UiState.Success(response.body()?.items ?: emptyList())
            } else {
                _itemsState.value =
                    UiState.Error("Failed to load items")
            }
        }
    }

    fun loadItem(itemId: String) {
        viewModelScope.launch {
            _itemState.value = UiState.Loading

            val response = repository.getItem(itemId)

            if (response.isSuccessful && response.body() != null) {
                _itemState.value =
                    UiState.Success(response.body()!!.item)
            } else {
                _itemState.value =
                    UiState.Error("Failed to load item")
            }
        }
    }

    fun createItem(
        userId: String,
        title: String,
        price: Double,
        link: String
    ) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading

            val response = repository.createItem(
                userId = userId,
                title = title,
                price = price,
                link = link
            )

            if (response.isSuccessful) {
                _actionState.value =
                    UiState.Success(response.body()!!.itemId)
            } else {
                _actionState.value =
                    UiState.Error("Failed to create item")
            }
        }
    }

    fun updateItem(
        itemId: String,
        userId: String,
        title: String,
        price: Double,
        link: String
    ) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading

            val response = repository.updateItem(
                itemId = itemId,
                userId = userId,
                title = title,
                price = price,
                link = link
            )

            if (response.isSuccessful) {
                _actionState.value =
                    UiState.Success(response.body()!!.itemId)
            } else {
                _actionState.value =
                    UiState.Error("Failed to update item")
            }
        }
    }

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading

            val response = repository.deleteItem(itemId)

            if (response.isSuccessful) {
                _actionState.value =
                    UiState.Success(itemId)
            } else {
                _actionState.value =
                    UiState.Error("Failed to delete item")
            }
        }
    }
}