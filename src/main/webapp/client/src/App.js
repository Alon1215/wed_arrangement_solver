import React, {Component} from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import './App.css';

import HomePage from "./pages/HomePage";
import LoginRegisterPage from "./pages/Register&Login";

class App extends Component {
    state = {
        isLoggedIn: false,
        isAdmin: false,
        username:"",
    }

  render(){
    return (
        <Router>
          {/*home-page*/}
          <Route exact path="/">
            <HomePage/>
          </Route>
          <Route path="/login&register">
              <LoginRegisterPage />
          </Route>
        </Router>
    );
  }
}

export default App;