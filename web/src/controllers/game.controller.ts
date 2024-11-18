import { Request, Response } from "express"
import { GameService } from "../services/game.service"
import { Player } from "../types/player"
import { HTTPError } from "../errors/HTTPError"

export class GameController {
	private gameService: GameService

	constructor(gameService: GameService) {
		this.gameService = gameService
	}

	async home(req: Request, res: Response) {
		res.render("home")
	}

	async board(req: Request, res: Response) {
		const game = await this.gameService.getGameById(req.params.id)

		if (!game) {
			throw new HTTPError("Game not found", 404)
		}

		res.render("board", {
			board: Array(3 * 3).fill(null),
			game,
		})
	}

	async start(req: Request, res: Response) {
		const player: Player = res.locals.player
		console.log(player)
		const game = await this.gameService.start({
			player,
		})
		console.log(game)
		res.status(200).json(game)
	}

	async move(req: Request, res: Response) {
		const { position } = req.body
		const id = req.params.id
		const player: Player = res.locals.player

		if (!position) {
			throw new HTTPError("Position is required", 400)
		}

		if (Number.parseInt(position) < 0 || Number.parseInt(position) > 8) {
			throw new HTTPError("Invalid position", 400)
		}

		const game = await this.gameService.move({
			id,
			player,
			position: Number.parseInt(position),
		})

		if (!game) {
			throw new HTTPError("Game not found", 404)
		}

		res.status(200).json(game)
	}
}
