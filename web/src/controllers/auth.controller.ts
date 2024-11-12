import { Request, Response } from "express"
import { AuthService } from "../services/auth.service"
import { HTTPError } from "../errors/HTTPError"

export class AuthController {
	private authService: AuthService

	constructor(authService: AuthService) {
		this.authService = authService
	}

	login(req: Request, res: Response) {
		throw new HTTPError("Teste")
		res.render("login")
	}
}
