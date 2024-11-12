import { NextFunction, Request, Response } from "express"
import { HTTPError } from "../errors/HTTPError"

export function errorHandlerMiddleware(error: unknown, req: Request, res: Response, next: NextFunction) {
	if (error instanceof HTTPError) {
		return res.status(error.statusCode).json({ message: error.message })
	}

	return res.status(500).json({
		message: "Internal server error",
	})
}
