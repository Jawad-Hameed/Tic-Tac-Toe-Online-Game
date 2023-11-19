package com.jawadjk.tictactoe

data class MatchModel(
    val id:String = "",
    val matched:Boolean = false,
    val firstPlayer:String = "",
    val secondPlayer:String = ""
)