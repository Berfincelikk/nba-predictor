package com.example.nba.data.model

data class Game(

    val homeTeam: String,
    val awayTeam: String,

    val homeProbability: Int,
    val awayProbability: Int,

    val homeForm: String,
    val awayForm: String,

    val arena: String,
    val city: String,
    val matchTime: String,
    val matchDate: String

)