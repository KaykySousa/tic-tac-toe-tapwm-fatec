import express from "express"
import { engine } from "express-handlebars"
import bodyParser from "body-parser"
import cookieParser from "cookie-parser"

import { gameRouter } from "./routes/game.routes"
import { authRouter } from "./routes/auth.routes"
import { errorHandlerMiddleware } from "./middlewares/error-handler.middleware"

export const app = express()

app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
app.use(cookieParser())

app.engine(
	"hbs",
	engine({
		extname: "hbs",
	})
)
app.set("view engine", "hbs")
app.set("views", __dirname + "/views")

app.use(express.static(__dirname + "/../public"))

app.use("/", gameRouter)
app.use("/auth", authRouter)

app.use(errorHandlerMiddleware)
