import { Request, Response } from "express"
import { AuthService } from "../services/auth.service"

export class AuthController {
	private authService: AuthService

	constructor(authService: AuthService) {
		this.authService = authService
	}

	login(req: Request, res: Response) {
		res.render("login")
	}
}
