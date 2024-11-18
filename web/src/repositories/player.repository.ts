import { db } from "../config/firebaseAdmin"
import { Player } from "../types/player"

export class PlayerRepository {
	async getById(id: string): Promise<Player | null> {
		const playerRef = db.collection("player").doc(id)
		const player = await playerRef.get()
		if (!player.exists) return null
		return {
			id: player.id,
			...player.data(),
		} as Player
	}
}
