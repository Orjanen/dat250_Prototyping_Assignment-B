import React, {Fragment} from 'react';
import {Menu, Icon, Dropdown} from "semantic-ui-react";
import {Link, NavLink} from "react-router-dom";

const NavBar = (props) => {

    const loginUser = window.localStorage.getItem('token')
    const role = window.localStorage.getItem('role')

    const logout = ()=>{
        window.localStorage.removeItem('token')
        window.localStorage.removeItem('userId')
        window.localStorage.removeItem('role')
    }

    return (
       <Fragment>
           <Menu fixed='top'>
               <Menu.Item
                   header as={NavLink}
                   to='/'
                   exact
                   style={{marginLeft: '10em'}}
               >
                   Welcome
               </Menu.Item>

               {loginUser &&<Menu.Item position='right' style={{marginRight: '10em'}}>
                   <Icon name='user'/>
                   <Dropdown pointing='top left'>
                       <Dropdown.Menu>
                           <Dropdown.Item
                               as={Link}
                               to={`/homepage/${window.localStorage.getItem('userId')}`}
                               text='Homepage'
                           />
                           <Dropdown.Item
                               as={Link}
                               to='/createnewpoll'
                               text='New poll'
                           />
                           <Dropdown.Item
                               as={Link}
                               to={`/profiledetails/${window.localStorage.getItem('userId')}`}
                               text='Edit profile'
                           />
                           {role === 'ROLE_ADMIN' && <Dropdown.Item
                               as={Link}
                               to={`/admin`}
                               text='Admin Page'
                           />}
                           <Dropdown.Item
                               as={Link}
                               to='/'
                               onClick={logout}
                               text='Logout'

                           />
                       </Dropdown.Menu>
                   </Dropdown>
               </Menu.Item>}
           </Menu>
       </Fragment>
    );
}

export default NavBar;
