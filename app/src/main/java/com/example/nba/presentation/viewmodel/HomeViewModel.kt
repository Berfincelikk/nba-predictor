package com.example.nba.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nba.data.model.GamePreviewResponse
import com.example.nba.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val games = mutableStateOf<List<GamePreviewResponse>>(emptyList())

    val isLoading = mutableStateOf(false)

    val errorMessage = mutableStateOf<String?>(null)

    init {
        loadGames()
    }

    private fun loadGames() {
        viewModelScope.launch {

            isLoading.value = true
            errorMessage.value = null

            try {
                val response = RetrofitClient.api.getGames(
                    startDate = "2026-10-25",
                    endDate = "2026-10-25"
                )

                games.value = response

            } catch (e: Exception) {

                errorMessage.value =
                    e.message ?: "Maçlar alınamadı."

            } finally {

                isLoading.value = false
            }
        }
    }
}