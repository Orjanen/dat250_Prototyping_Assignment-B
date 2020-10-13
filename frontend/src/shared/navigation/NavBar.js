import React, {Fragment} from 'react';
import {Menu, Icon, Dropdown} from "semantic-ui-react";
import {Link, NavLink} from "react-router-dom";

const NavBar = () => {
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

               <Menu.Item position='right' style={{marginRight: '10em'}}>
                   <Icon name='user'/>
                   <Dropdown pointing='top left'>
                       <Dropdown.Menu>
                           <Dropdown.Item
                               as={Link}
                               to='/homepage/123'
                               text='Homepage'
                           />
                           <Dropdown.Item
                               as={Link}
                               to='/createnewpoll'
                               text='New poll'
                           />
                           <Dropdown.Item
                               as={Link}
                               to='/profiledetails'
                               text='Edit profile'
                           />
                           <Dropdown.Item
                               as={Link}
                               to='/'
                               text='Logout'
                           />
                       </Dropdown.Menu>
                   </Dropdown>
               </Menu.Item>
           </Menu>
       </Fragment>
    );
}

export default NavBar;
