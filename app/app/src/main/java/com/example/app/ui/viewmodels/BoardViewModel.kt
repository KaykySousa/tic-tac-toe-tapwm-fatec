package com.example.app.ui.viewmodels

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.app.data.Game
import com.example.app.data.GameStatus
import com.example.app.data.Move
import com.example.app.ui.repositories.GameRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BoardViewModel(
    private val gameId: String
): ViewModel() {
    private val gameRepository: GameRepository = GameRepository()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    val board = MutableStateFlow<List<String?>>(List<String?>(9) { null })
    val isMyTurn = MutableStateFlow<Boolean>(false)

    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> get() = statusMessage

    fun move(
        position: Int,
    ) {
        println("POSITION: ${position}")
        viewModelScope.launch {
            val game = gameRepository.getById(gameId)

            val moves = game.moves!!.toMutableList()
            moves.add(
                Move(
                    position = position,
                    playerId = auth.currentUser!!.uid
                )
            )
            val updatedGame = gameRepository.update(gameId, Game(
                moves = moves
            ))

            val winningCombinations = listOf(
                listOf(0, 1, 2),
                listOf(3, 4, 5),
                listOf(6, 7, 8),
                listOf(0, 3, 6),
                listOf(1, 4, 7),
                listOf(2, 5, 8),
                listOf(0, 4, 8),
                listOf(2, 4, 6)
            )

            val playerMoves = updatedGame.moves!!.filter { move -> move.playerId == auth.currentUser!!.uid }
            val playerPositions = playerMoves.map { move -> move.position }

            for (combination in winningCombinations) {
                if (combination.all { position -> playerPositions.contains(position) }) {
                    gameRepository.update(
                        gameId, Game(
                            status = if (auth.currentUser!!.uid == game.playerX?.id) {
                                GameStatus.X_WINNER
                            } else {
                                GameStatus.O_WINNER
                            }
                        )
                    )
                }
            }

            if (updatedGame.moves.size >= 9) {
                gameRepository.update(
                    gameId, Game(
                        status = GameStatus.DRAW
                    )
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            println("GAME_ID: ${gameId}")
            val gameRef = db.collection("games").document(gameId)

            gameRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("BoardViewModel", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val game = snapshot.toObject<Game>()!!.copy(id = snapshot.id)

                    val newBoard = List<String?>(9) { null }.toMutableList()
                    game.moves?.forEach { move ->
                        newBoard[move.position!!] = if (move.playerId == game.playerX?.id) { "X" } else { "O" }
                    }

                    val turnPlayerId = if (game.moves!!.size % 2 == 0) { game.playerX?.id } else { game.playerO?.id }
                    isMyTurn.value = turnPlayerId == auth.currentUser!!.uid

                    board.value = newBoard

                    if ((game.status == GameStatus.X_WINNER && auth.currentUser!!.uid == game.playerX?.id) ||
                        (game.status == GameStatus.O_WINNER && auth.currentUser!!.uid == game.playerO?.id)) {
                        statusMessage.value = "Parabéns, você venceu! \uD83C\uDF89"

                    } else if ((game.status == GameStatus.X_WINNER && auth.currentUser!!.uid == game.playerO?.id) ||
                        (game.status == GameStatus.O_WINNER && auth.currentUser!!.uid == game.playerX?.id)) {
                        statusMessage.value = "Você perdeu! \uD83D\uDE22"

                    } else if (game.status == GameStatus.DRAW) {
                        statusMessage.value = "Empate! \uD83D\uDE15"

                    } else if (game.status == GameStatus.WITHDRAW) {
                        statusMessage.value = "Partida abandonada! \uD83D\uDE22"
                    }
                }
            }
        }
    }
}