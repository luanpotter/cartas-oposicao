import React, { Component } from "react";
import "./App.css";
import * as firebase from "firebase";

firebase.initializeApp({
  apiKey: "AIzaSyCERrddd8CzLd3axsLfEYhq0xiiflmQsvI",
  authDomain: "cartas-oposicao.firebaseapp.com",
  databaseURL: "https://cartas-oposicao.firebaseio.com",
  projectId: "cartas-oposicao",
  storageBucket: "cartas-oposicao.appspot.com",
  messagingSenderId: "153053898759"
});

const API = window.location.href.includes("localhost") ? "http://localhost:8082/api/" : "/api";

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
    this.fetchUser(login);
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Gerador de Cartas de Oposição</h1>
        </header>
        <p className="App-intro">{this.router()}</p>
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
    return <span>Hello, {this.state.login.user}</span>
  }

  loginPage() {
    return <button onClick={() => this.doLogin()}>Login</button>;
  }

  doLogin() {
    const provider = new firebase.auth.GoogleAuthProvider();
    firebase
      .auth()
      .signInWithPopup(provider)
      .then(result => {
        const token = result.credential.accessToken;
        const login = { token };
        localStorage.setItem("login_data", JSON.stringify(login));
        this.fetchUser(login);
      })
      .catch(error => console.log(error));
  }

  fetchUser(login) {
    fetch(API + "users/me", this.headers(login))
      .then(me => {
        console.log(me);
        login.user = me;
        this.setState({ login });
      })
      .catch(error => console.log(error));
  }

  headers(login) {
    return { headers: { Authorization: 'Bearer login.token' } };
  }
}

export default App;
