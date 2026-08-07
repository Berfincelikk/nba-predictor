package com.example.nba.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nba.data.model.Game
import com.example.nba.data.repository.FakeRepository

class HomeViewModel : ViewModel() {

    val games = mutableStateOf<List<Game>>(emptyList())

    init {
        loadGames()
    }

    private fun loadGames() {
        games.value = FakeRepository.getGames()
    }

}