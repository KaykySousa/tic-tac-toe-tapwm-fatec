import { db } from "../config/firebaseAdmin"
import { Game, GameStatus } from "../types/game"

export class GameRepository {
	async getAll(): Promise<Game[]> {
		const gamesRef = db.collection("games")
		const games = await gamesRef.get()
		return games.docs.map(
			(doc) =>
				({
					id: doc.id,
					...doc.data(),
				} as Game)
		)
	}

	async getByStatus(status: GameStatus): Promise<Game[]> {
		const gamesRef = db.collection("games")
		const games = await gamesRef.where("status", "==", status).get()
		return games.docs.map(
			(doc) =>
				({
					id: doc.id,
					...doc.data(),
				} as Game)
		)
	}

	async getById(id: string): Promise<Game | null> {
		const gameRef = db.collection("games").doc(id)
		const game = await gameRef.get()
		if (!game.exists) return null
		return {
			id: game.id,
			...game.data(),
		} as Game
	}

	async create(game: Omit<Game, "id" | "started_at">): Promise<Game> {
		const gamesRef = db.collection("games")
		const newGameRef = await gamesRef.add(game)
		const newGame = await newGameRef.get()
		return {
			id: newGame.id,
			...newGame.data(),
		} as Game
	}

	async update(id: string, game: Partial<Game>): Promise<Game> {
		const gameRef = db.collection("games").doc(id)
		await gameRef.update(game)
		return (await this.getById(id))!
	}
}
