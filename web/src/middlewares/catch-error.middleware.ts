import { NextFunction, Request, Response } from "express"

export function catchErrorMiddleware(action: (req: Request, res: Response) => Promise<void>) {
	return async (req: Request, res: Response, next: NextFunction) => {
		try {
			await action(req, res)
		} catch (error) {
			next(error)
		}
	}
}
