import { HTTPError } from "../errors/HTTPError"
import { GameRepository } from "../repositories/game.repository"
import { Player } from "../types/player"

export class GameService {
	private gameRepository: GameRepository

	constructor(gameRepository: GameRepository) {
		this.gameRepository = gameRepository
	}

	async start({ player }: { player: Player }) {
		const searchingGames = await this.gameRepository.getByStatus("searching")
		const availableGames = searchingGames.filter((game) => game.playerX?.id !== player.id)

		if (availableGames.length <= 0) {
			return await this.gameRepository.create({
				status: "searching",
				moves: [],
				playerX: player,
			})
		}

		return await this.gameRepository.update(availableGames[0].id, {
			status: "in_progress",
			playerO: player,
		})
	}

	async getGameById(id: string) {
		return await this.gameRepository.getById(id)
	}

	async move({ id, player, position }: { id: string; player: Player; position: number }) {
		const game = await this.gameRepository.getById(id)
		if (!game) return null

		game.moves.forEach((move) => {
			if (move.position === position) {
				throw new HTTPError("Position already taken", 400)
			}
		})

		const updatedGame = await this.gameRepository.update(game.id, {
			moves: [
				...game.moves,
				{
					playerId: player.id,
					position,
				},
			],
		})

		const winningCombinations = [
			[0, 1, 2],
			[3, 4, 5],
			[6, 7, 8],
			[0, 3, 6],
			[1, 4, 7],
			[2, 5, 8],
			[0, 4, 8],
			[2, 4, 6],
		]

		const playerMoves = updatedGame.moves.filter((move) => move.playerId === player.id)
		const playerPositions = playerMoves.map((move) => move.position)

		for (const combination of winningCombinations) {
			if (combination.every((position) => playerPositions.includes(position))) {
				return await this.gameRepository.update(updatedGame.id, {
					status: player.id === updatedGame.playerX?.id ? "x_winner" : "o_winner",
				})
			}
		}

		if (updatedGame.moves.length >= 9) {
			return await this.gameRepository.update(updatedGame.id, {
				status: "draw",
			})
		}

		return updatedGame
	}
}
