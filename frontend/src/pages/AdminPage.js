import React, {Fragment, useEffect, useState} from 'react';
import {Segment, List, Button, Header} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";
import agent from "../api/agent";

const AdminPage = (props) => {
    const [users, setUsers] = useState([]);
    const [poll, setPolls] = useState([]);

    useEffect( () =>{
        const getUsers = async () =>{
            try {
                await  agent.User.getAll().then(response => {
                    setUsers(response)
                })
            }catch (e){
                console.log('Some thing went wrong')
            }
        }
        getUsers()

    },[users])

    const banUser = async (id) =>{
        try {
            await agent.User.banUser(id)
        }catch (e){
            console.log('cant ban the user')
        }
        users.forEach(user => {
            if(user.userId === id) {
                user.banStatus = !user.banStatus
            }
        })
    }

    return (
        <Fragment>
        <Segment style={{marginTop: '7em'}}>
            <div style={{textAlign: "center", marginBottom: '5em'}}>
                <IconHeader
                    icon='user secret'
                    mainText='Admin Page'
                />
            </div>
            <Header style={{textAlign: "center"}}> Users</Header>
            {users && users.map(user => (
                <List divided verticalAlign='middle' key={user.userId}>
                    <List.Item>
                        <List.Content floated='right'>
                            <Button color={user.banStatus ? 'green' : 'red'}
                            onClick={() => banUser(user.userId)}
                            >{user.banStatus ? 'Unban' : 'ban'}</Button>
                        </List.Content>
                        <List.Content>{user.firstName} {user.lastName}</List.Content>
                    </List.Item>
                </List>
            ))}

        </Segment>

            <Segment>
                <Header style={{textAlign: "center"}}> Poll</Header>
            </Segment>
        </Fragment>
    );
}

export default AdminPage;