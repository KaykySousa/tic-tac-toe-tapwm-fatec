package com.example.app.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.Game
import com.example.app.data.GameStatus
import com.example.app.data.Player
import com.example.app.ui.repositories.GameRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val gameRepository: GameRepository = GameRepository()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var listenerRegistration: ListenerRegistration? = null

    val gameId = MutableStateFlow<String?>(null)

    suspend fun start(onFindGame: (id: String) -> Unit) {
        val searchingGames = gameRepository.getByStatus(GameStatus.SEARCHING)
        val availableGames = searchingGames.filter { game ->
            game.playerX?.id != auth.currentUser!!.uid
        }

        if (availableGames.size <= 0) {
            val newGame = gameRepository.create(Game(
                status = GameStatus.SEARCHING,
                moves = emptyList(),
                playerX = Player(
                    id = auth.currentUser!!.uid,
                    name = auth.currentUser!!.displayName ?: "[Jogador sem nome]"
                ),
            ))

            val gameRef = db.collection("games").document(newGame.id!!)

            listenerRegistration = gameRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("HomeViewModel", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val game = snapshot.toObject<Game>()!!.copy(id = snapshot.id)

                    if (game.status == GameStatus.IN_PROGRESS) {
                        listenerRegistration?.remove()
                        listenerRegistration = null
                        onFindGame(game.id!!)
                    }
                }
            }

            return
        }

        gameRepository.update(availableGames[0].id!!, Game(
            status = GameStatus.IN_PROGRESS,
            playerO = Player(
                id = auth.currentUser!!.uid,
                name = auth.currentUser!!.displayName ?: "[Jogador sem nome]"
            )
        ))

        onFindGame(availableGames[0].id!!)
    }

}