import { NextFunction, Request, Response } from "express"

export function catchErrorMiddleware(
	...actions: ((req: Request, res: Response, next: NextFunction) => Promise<void>)[]
) {
	return actions.map((action) => {
		return async (req: Request, res: Response, next: NextFunction) => {
			try {
				await action(req, res, next)
			} catch (error) {
				next(error)
			}
		}
	})
}
