import React, {Fragment, } from 'react';
import {Route, Switch} from 'react-router-dom'
import {Container} from 'semantic-ui-react'

import welcomePage from './pages/welcomePage'
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import NavBar from "./shared/navigation/NavBar";
import CreatePollPage from "./pages/CreatePollPage";
import ProfileDetails from "./pages/ProfileDetails";
import VotePage from "./pages/VotePage";
import SignupPage from "./pages/SignupPage";

const App = () => {
  return (
        <Fragment>
            <NavBar/>
            <Route path='/' exact component={welcomePage}/>
            <Route path={'/(.+)'} render={() => (
                <Fragment>
                    <Container>
                        <Switch>
                            <Route path='/login' exact component={LoginPage}/>
                            <Route path='/homepage/:id' exact component={HomePage}/>
                            <Route path='/createnewpoll' exact component={CreatePollPage}/>
                            <Route path='/poll/:pollId' exact component={VotePage}/>
                            <Route path='/profiledetails' exact component={ProfileDetails}/>
                            <Route path='/signup' exact component={SignupPage}/>
                        </Switch>
                    </Container>
                </Fragment>
            )}/>
        </Fragment>
    );
}

export default App;
