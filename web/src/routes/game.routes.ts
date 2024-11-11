import { Router } from "express"

import { GameRepository } from "../repositories/game.repository"
import { GameService } from "../services/game.service"
import { GameController } from "../controllers/game.controller"
import { db } from "../config/firebaseAdmin"

export const gameRouter = Router()

const gameRepository = new GameRepository()
const gameService = new GameService(gameRepository)
const gameController = new GameController(gameService)

gameRouter.get("/", gameController.index)
gameRouter.post("/move", gameController.move)
