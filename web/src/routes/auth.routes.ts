import { Router } from "express"

import { AuthController } from "../controllers/auth.controller"
import { AuthService } from "../services/auth.service"

export const authRouter = Router()

const authService = new AuthService()
const authController = new AuthController(authService)

authRouter.get("/login", authController.login)
