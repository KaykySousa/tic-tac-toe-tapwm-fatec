package com.example.app.data

object GameStatus {
    const val IN_PROGRESS = "in_progress"
    const val DRAW = "draw"
    const val X_WINNER = "x_winner"
    const val O_WINNER = "o_winner"
    const val WITHDRAW = "withdraw"
    const val SEARCHING = "searching"
}

data class Move(
    val playerId: String? = null,
    val position: Int? = null
)

data class Game(
    val id: String? = null,
    val playerX: Player? = null,
    val playerO: Player? = null,
    val status: String? = null,
    val moves: List<Move>? = null
)

data class GameCreation(
    val playerX: Player,
    val status: String,
    val moves: List<Move>
)