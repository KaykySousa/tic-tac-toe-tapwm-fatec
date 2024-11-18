import { initializeApp, cert } from "firebase-admin/app"
import { getFirestore } from "firebase-admin/firestore"
import { getAuth } from "firebase-admin/auth"

const app = initializeApp({
	credential: cert(__dirname + "/serviceAccount.json"),
})

export const db = getFirestore(app)
export const auth = getAuth(app)
