import React, {Component} from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import './App.css';

import HomePage from "./pages/HomePage";

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
        </Router>
    );
  }
}

export default App;