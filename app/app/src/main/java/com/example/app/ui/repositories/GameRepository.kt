package com.example.app.ui.repositories

import com.example.app.data.Game
import com.example.app.data.GameCreation
import com.example.app.data.GameStatus
import com.example.app.data.Move
import com.example.app.data.Player
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await

class GameRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getById(id: String): Game {
        val gameRef = db.collection("games").document(id)
        val game = gameRef.get().await()

        return game.toObject<Game>()!!.copy(id = game.id)
    }


    suspend fun getByStatus(status: String): List<Game> {
        val gamesRef = db.collection("games")
        val games = gamesRef.whereEqualTo("status", status).get().await()

        return games.documents.map { doc ->
            doc.toObject<Game>()!!.copy(id = doc.id)
        }
    }

    suspend fun create(game: Game): Game {
        val gamesRef = db.collection("games")

        val nonNullFields = mutableMapOf<String, Any>()
        game.playerX?.let { nonNullFields["playerX"] = it }
        game.playerO?.let { nonNullFields["playerO"] = it }
        game.status?.let { nonNullFields["status"] = it }
        game.moves?.let { nonNullFields["moves"] = it }

        val newGameRef = gamesRef.add(nonNullFields).await()
        val newGame = newGameRef.get().await()

        return newGame.toObject<Game>()!!.copy(id = newGame.id)
    }


    suspend fun update(id: String, game: Game): Game {
        val gameRef = db.collection("games").document(id)

        val updatedFields = mutableMapOf<String, Any>()
        game.playerX?.let { updatedFields["playerX"] = it }
        game.playerO?.let { updatedFields["playerO"] = it }
        game.status?.let { updatedFields["status"] = it }
        game.moves?.let { updatedFields["moves"] = it }

        gameRef.update(updatedFields).await()
        val updatedGame = gameRef.get().await()

        return updatedGame.toObject<Game>()!!.copy(id = updatedGame.id)
    }
}