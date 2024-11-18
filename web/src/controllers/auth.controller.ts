import { Request, Response } from "express"
import { AuthService } from "../services/auth.service"
import { HTTPError } from "../errors/HTTPError"

export class AuthController {
	private authService: AuthService

	constructor(authService: AuthService) {
		this.authService = authService
	}

	async login(req: Request, res: Response) {
		res.render("login")
	}
}
