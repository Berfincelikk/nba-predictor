package com.example.nba.data.remote

import com.example.nba.data.model.GameDetailResponse
import com.example.nba.data.model.GamePreviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NbaPredictionApi {

    @GET("games")
    suspend fun getGames(
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null
    ): List<GamePreviewResponse>

    @GET("games/{game_id}/detail")
    suspend fun getGameDetail(
        @Path("game_id") gameId: String
    ): GameDetailResponse
}