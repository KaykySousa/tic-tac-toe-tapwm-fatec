import { Router } from "express"

import { AuthController } from "../controllers/auth.controller"
import { AuthService } from "../services/auth.service"
import { catchErrorMiddleware } from "../middlewares/catch-error.middleware"
import { PlayerRepository } from "../repositories/player.repository"

export const authRouter = Router()

const playerRepository = new PlayerRepository()
const authService = new AuthService(playerRepository)
const authController = new AuthController(authService)

authRouter.get("/login", catchErrorMiddleware(authController.login))
