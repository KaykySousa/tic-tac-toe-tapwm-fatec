import { NextFunction, Request, Response } from "express"
import { auth } from "../config/firebaseAdmin"
import { AuthService } from "../services/auth.service"
import { PlayerRepository } from "../repositories/player.repository"

export async function authMiddleware(req: Request, res: Response, next: NextFunction) {
	const playerRepository = new PlayerRepository()
	const authService = new AuthService(playerRepository)

	const token = req.cookies["tictactoe.token"]

	if (!token) return res.redirect("/auth/login")

	let uid: string
	try {
		const decoded = await auth.verifyIdToken(token)
		uid = decoded.uid
	} catch (error) {
		console.log("REVOGADO")
		return res.redirect("/auth/login")
	}

	const player = await authService.getPlayer(uid)
	console.log("PLAYER", player)

	if (!player) return res.redirect("/auth/login")

	res.locals.player = player

	next()
}
