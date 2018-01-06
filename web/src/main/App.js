import React, { Component } from "react";
import * as firebase from "firebase";
import { FirebaseAuth } from "react-firebaseui";

import "main/App.css";
import Generator from "generator/Generator";
import Admin from "admin/Admin";

const uiConfig = {
  signInFlow: "popup",
  signInOptions: [firebase.auth.GoogleAuthProvider.PROVIDER_ID],
  callbacks: {
    signInSuccess: () => false
  }
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

const bakeFetch = login => (route, method = "GET", data = null) => {
  if (route.startsWith('/')) {
    route = route.substr(1);
  }
  return fetch(API + route, options(method, login, data));
};

const options = (method, login, data) => {
  return {
    method,
    body: data ? JSON.stringify(data) : undefined,
    headers: { Authorization: "Bearer " + login.idToken }
  };
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
          <span className="App-person-card">
            <span>
              Hello{this.state.login ? `, ${this.state.login.name}!` : "!"}
            </span>
            {this.state.login ? (
              <span className="click" onClick={() => this.logout()}>
                {" "}
                | Logout
              </span>
            ) : (
              ""
            )}
          </span>
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
    const isRH = window.location.href.endsWith("/rh");
    const C = isRH ? Admin : Generator;
    return <C login={this.state.login} fetch={bakeFetch(this.state.login)} />;
  }

  loginPage() {
    return <FirebaseAuth uiConfig={uiConfig} firebaseAuth={firebase.auth()} />;
  }

  logout() {
    localStorage.setItem("login_data", null);
    this.setState({ login: null });
  }

  async fetchUser(login) {
    const resp = await bakeFetch(login)("users/me");
    login.user = await resp.json();
    this.setState({ login });
  }
}

export default App;
