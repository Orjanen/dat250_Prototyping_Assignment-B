import React, {Fragment, useEffect, useState} from 'react';
import {Segment, List, Button, Header} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";
import agent from "../api/agent";

const AdminPage = (props) => {
    const [users, setUsers] = useState([]);
    const [polls, setPolls] = useState([]);

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

    useEffect( () =>{
        const getPolls = async () =>{
            try {
                await  agent.Poll.getAll().then(response => {
                    setPolls(response)
                })
            }catch (e){
                console.log('Some thing went wrong')
            }
        }
        getPolls()

    },[polls])

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

    const deletePoll = async (id) =>{
        try {
            await agent.Poll.delete(id)
        }catch (e){
            console.log('cant delete poll')
        }

        setPolls(polls.filter(poll => {
           return  poll.pollId !== id
        }))
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
                {polls && polls.map(poll => (
                    <List divided verticalAlign='middle' key={poll.pollId}>
                        <List.Item>
                            <List.Content floated='right'>
                                <Button color='red'
                                        onClick={() =>{deletePoll(poll.pollId)}}
                                >Delete</Button>
                            </List.Content>
                            <List.Content>{`${poll.pollName}`}: {poll.pollId}</List.Content>
                        </List.Item>
                    </List>
                ))}
            </Segment>
        </Fragment>
    );
}

export default AdminPage;