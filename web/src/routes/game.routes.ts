import { Router } from "express"

import { GameRepository } from "../repositories/game.repository"
import { GameService } from "../services/game.service"
import { GameController } from "../controllers/game.controller"
import { authMiddleware } from "../middlewares/auth.middleware"
import { catchErrorMiddleware } from "../middlewares/catch-error.middleware"

export const gameRouter = Router()

const gameRepository = new GameRepository()
const gameService = new GameService(gameRepository)
const gameController = new GameController(gameService)

gameRouter.get("/", catchErrorMiddleware(authMiddleware, gameController.home.bind(gameController)))
gameRouter.get("/board/:id", catchErrorMiddleware(authMiddleware, gameController.board.bind(gameController)))
gameRouter.post("/move/:id", catchErrorMiddleware(authMiddleware, gameController.move.bind(gameController)))
gameRouter.post("/start", catchErrorMiddleware(authMiddleware, gameController.start.bind(gameController)))
