import { auth } from "../config/firebaseAdmin"
import { PlayerRepository } from "../repositories/player.repository"
import { Player } from "../types/player"

export class AuthService {
	private playerRepository: PlayerRepository

	constructor(playerRepository: PlayerRepository) {
		this.playerRepository = playerRepository
	}

	async getPlayer(id: string): Promise<Player | null> {
		const authUser = await auth.getUser(id)
		// const dbUser = await this.playerRepository.getById(id)

		// if (!authUser || !dbUser) return null
		if (!authUser) return null

		return {
			id,
			name: authUser.displayName ?? "[Jogador sem nome]",
			// rating: dbUser.rating,
		} as Player
	}
}
