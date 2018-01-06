import React, { Component } from "react";
import * as firebase from "firebase";
import { FirebaseAuth } from "react-firebaseui";

import "main/App.css";

const uiConfig = {
  signInFlow: "popup",
  signInOptions: [firebase.auth.GoogleAuthProvider.PROVIDER_ID]
};

firebase.initializeApp({
  apiKey: "AIzaSyCERrddd8CzLd3axsLfEYhq0xiiflmQsvI",
  authDomain: "cartas-oposicao.firebaseapp.com",
  databaseURL: "https://cartas-oposicao.firebaseio.com",
  projectId: "cartas-oposicao",
  storageBucket: "cartas-oposicao.appspot.com",
  messagingSenderId: "153053898759"
});

const API = window.location.href.includes("localhost")
  ? "http://localhost:8082/api/"
  : "/api/";

const getLogin = () => {
  const data = localStorage.getItem("login_data");
  if (data) {
    return JSON.parse(data);
  }
  return null;
};

class App extends Component {
  constructor(props) {
    super(props);
    const login = getLogin();
    this.state = { login };
    if (login) {
      this.fetchUser(login);
    }
  }

  componentDidMount() {
    firebase.auth().onAuthStateChanged(user => {
      if (user) {
        const email = user.email;
        const name = user.displayName || email;

        user.getIdToken().then(idToken => {
          const login = { email, name, idToken };
          localStorage.setItem("login_data", JSON.stringify(login));
          this.setState({ login });
          this.fetchUser(login);
        });
      } else {
        console.error("No user, mate!");
      }
    });
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Gerador de Cartas de Oposição</h1>
        </header>
        <div className="App-intro">{this.router()}</div>
      </div>
    );
  }

  router() {
    if (!this.state.login) {
      return this.loginPage();
    }
    if (!this.state.login.user) {
      return <span>Loading user info...</span>;
    }
    return <span>Hello, {this.state.login.user.email}</span>;
  }

  loginPage() {
    return <FirebaseAuth uiConfig={uiConfig} firebaseAuth={firebase.auth()} />;
  }

  async fetchUser(login) {
    const resp = await fetch(API + "users/me", this.headers(login));
    login.user = await resp.json();
    console.log(login);
    this.setState({ login });
  }

  headers(login) {
    return { headers: { Authorization: "Bearer " + login.idToken } };
  }
}

export default App;
