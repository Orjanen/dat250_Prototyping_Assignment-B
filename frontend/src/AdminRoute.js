import React from 'react';
import {Route, Redirect} from 'react-router-dom'

const AdminRoute = (props) => {
    const {component, ...rest} = props
    const Component = component
    const token = window.localStorage.getItem('token')
    const role = window.localStorage.getItem('role')

    return (
        <Route
            {...rest}
            render={(props => token && role === 'ROLE_ADMIN' ?
                <Component {...props}/> : <Redirect to={'/'}/>)}
        />
    );
}

export default AdminRoute;