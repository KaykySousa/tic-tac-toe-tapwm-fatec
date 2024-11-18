import { initializeApp } from "https://www.gstatic.com/firebasejs/11.0.1/firebase-app.js"
import { getAuth } from "https://www.gstatic.com/firebasejs/11.0.1/firebase-auth.js"
import { getFirestore } from "https://www.gstatic.com/firebasejs/11.0.1/firebase-firestore.js"

const firebaseConfig = {
	apiKey: "AIzaSyC8B0oYVI5dNBow8giUZTYeCOpgv4Ye-xM",
	authDomain: "web-app-project-1b4c6.firebaseapp.com",
	projectId: "web-app-project-1b4c6",
	storageBucket: "web-app-project-1b4c6.firebasestorage.app",
	messagingSenderId: "968623658084",
	appId: "1:968623658084:web:ea48cb61e06f4fad8abb71",
}

const app = initializeApp(firebaseConfig)

export const auth = getAuth(app)
export const db = getFirestore(app)
