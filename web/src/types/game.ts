import { Player } from "./player"

export type GameStatus = "in_progress" | "draw" | "x_winner" | "o_winner" | "withdraw" | "searching"

export interface Move {
	playerId: string
	position: number
}

export interface Game {
	id: string
	playerX?: Player
	playerO?: Player
	status: GameStatus
	started_at: string
	moves: Move[]
}
