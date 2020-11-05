import React, {useEffect, useState} from 'react';
import {List, Button, Segment, Label} from "semantic-ui-react";

import IconHeader from "../components/header/IconHeader";
import agent from "../api/agent";

const MyPollPage = (props) => {

    const [user, setUser] = useState({})

    useEffect( () =>{
        const getUser = async (id) =>{
            try {
                await  agent.User.details(id).then(response => {
                    setUser(response)
                })
            }catch (e){
                console.log('Some thing went wrong')
            }
        }
        getUser(props.match.params.id)

    },[props.match.params.id])

    return (
        <Segment style={{marginTop: '7em'}}>
            <div style={{textAlign: "center"}}>
                <IconHeader
                    icon='pie graph'
                    mainText='My Polls'
                />
            </div>
            {user.myPolls && user.myPolls.map(poll =>(
                <List key={poll.pollId} divided verticalAlign='middle'>
                    <List.Item>
                        <List.Content floated='right'>
                            <Button primary
                                    onClick={() =>{props.history.push(`/poll/${poll.pollId}/result`)}}
                            >Result</Button>
                            <Button basic
                                    onClick={() =>{props.history.push(`/poll/${poll.pollId}`)}}
                            >Vote Page</Button>
                        </List.Content>
                        <List.Content>
                            {poll.timeRemaining === 'PT0S' ? <Label as='a' color='red' ribbon>
                                Closed
                            </Label>
                                :<Label as='a' color='green' ribbon>
                                Open
                            </Label>}
                            {`${poll.pollName}`} </List.Content>
                    </List.Item>
                </List>
                ))}

        </Segment>

    );
}

export default MyPollPage;