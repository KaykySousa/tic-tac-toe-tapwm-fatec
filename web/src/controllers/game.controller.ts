import { Request, Response } from "express"
import { GameService } from "../services/game.service"

export class GameController {
	private gameService: GameService

	constructor(gameService: GameService) {
		this.gameService = gameService
	}

	index(req: Request, res: Response) {
		console.log(req.cookies)
		res.render("game", {
			board: Array(3 * 3).fill(null),
		})
	}

	move(req: Request, res: Response) {
		const { position } = req.body

		if (!position) {
			res.status(400).json({ message: "Missing parameters" })
		}

		console.log(position)

		res.status(200).end()
	}
}
