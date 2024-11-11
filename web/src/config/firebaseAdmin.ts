import { initializeApp, cert } from "firebase-admin/app"
import { getFirestore } from "firebase-admin/firestore"

console.log(process.env)

const app = initializeApp({
	credential: cert(__dirname + "/serviceAccount.json"),
})

export const db = getFirestore(app)
