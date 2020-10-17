import React from 'react';
import {Route, Redirect} from 'react-router-dom'

const PrivateRoute = (props) => {
    const {component, ...rest} = props
    const Component = component
    const token = window.localStorage.getItem('token')
    return (
        <Route
            {...rest}
            render={(props => token ?
                <Component {...props}/> : <Redirect to={'/'}/>)}
        />
    );
}

export default PrivateRoute;