package com.example.wishlistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.api.models.ItemModel
import com.example.wishlistapp.repository.SearchRepository
import com.example.wishlistapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository()

    private val _itemsState = MutableStateFlow<UiState<List<ItemModel>>>(UiState.Idle)
    val itemsState: StateFlow<UiState<List<ItemModel>>> = _itemsState

    fun searchItems(userId: String) {
        viewModelScope.launch {
            _itemsState.value = UiState.Loading
            try {
                val response = repository.getItemsByUserId(userId)
                if (response.isSuccessful) {
                    _itemsState.value = UiState.Success(response.body() ?: emptyList())
                } else {
                    _itemsState.value = UiState.Error("Failed to load items")
                }
            } catch (e: Exception) {
                _itemsState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
