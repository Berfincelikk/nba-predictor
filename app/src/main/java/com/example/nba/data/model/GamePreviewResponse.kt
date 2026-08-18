package com.example.nba.data.model

data class GamePreviewResponse(
    val gameId: String,
    val homeTeam: String,
    val awayTeam: String,
    val matchDate: String,
    val matchTime: String,
    val arena: String
)