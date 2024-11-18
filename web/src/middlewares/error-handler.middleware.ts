import { NextFunction, Request, Response } from "express"
import { HTTPError } from "../errors/HTTPError"

export function errorHandlerMiddleware(error: Error, req: Request, res: Response, next: NextFunction) {
	console.error(error)

	if (error instanceof HTTPError) {
		return res.status(error.statusCode).json({ message: error.message })
	}

	return res.status(500).json({
		message: error.message,
	})
}
