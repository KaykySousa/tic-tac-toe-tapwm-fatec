import { GameRepository } from "../repositories/game.repository"

export class GameService {
	private gameRepository: GameRepository

	constructor(gameRepository: GameRepository) {
		this.gameRepository = gameRepository
	}
}
