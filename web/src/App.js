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
    this.state = {
      login: getLogin()
    };
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Gerador de Cartas de Oposição</h1>
        </header>
        <p className="App-intro">
          {this.state.login ? this.state.login.token : this.loginPage()}
        </p>
      </div>
    );
  }

  loginPage() {
    return <button onClick={() => this.doLogin()}>Login</button>;
  }

  doLogin() {
    const provider = new firebase.auth.GoogleAuthProvider();
    firebase.auth().signInWithPopup(provider).then(result => {
      const token = result.credential.accessToken;
      const login = { token };
      localStorage.setItem('login_data', JSON.stringify(login));
      this.setState({ login });
    }).catch(error => console.log(JSON.stringify(error)));
  }
}

export default App;
