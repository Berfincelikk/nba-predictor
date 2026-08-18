package com.example.nba.data.repository

import com.example.nba.data.model.GameDetailResponse
import com.example.nba.data.model.GamePreviewResponse
import com.example.nba.data.remote.NbaPredictionApi

class NbaRepository(
    private val api: NbaPredictionApi
) {

    suspend fun getGames(
        startDate: String? = null,
        endDate: String? = null
    ): List<GamePreviewResponse> {
        return api.getGames(
            startDate = startDate,
            endDate = endDate
        )
    }

    suspend fun getGameDetail(
        gameId: String
    ): GameDetailResponse {
        return api.getGameDetail(gameId)
    }
}