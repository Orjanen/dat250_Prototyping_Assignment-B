import React, {Fragment} from 'react';
import {Route, Switch,  BrowserRouter as Router} from 'react-router-dom'

import welcomePage from './pages/welcomePage'
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import NavBar from "./shared/navigation/NavBar";

const App = () => {
  return (
      <Fragment>
          <Router>
          <Route path='/' exact component={welcomePage}/>
          <Route path={'/(.+)'} render={() => (
              <Fragment>
                  <NavBar/>
                      <Switch>
                          <Route path='/login' exact component={LoginPage}/>
                          <Route path='/homepage/:id' exact component={HomePage}/>
                      </Switch>
              </Fragment>
          )}/>

          </Router>
      </Fragment>
  );
}

export default App;
