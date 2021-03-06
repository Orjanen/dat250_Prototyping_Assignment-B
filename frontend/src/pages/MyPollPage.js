import React, {useEffect, useState} from 'react';
import {List, Button, Segment, Label, Modal} from "semantic-ui-react";

import IconHeader from "../components/header/IconHeader";
import agent from "../api/agent";
import HandleIotDevices from "../shared/IOT/HandleIotDevices";
import {cleanup} from "@testing-library/react";

const MyPollPage = (props) => {



    const [user, setUser] = useState({})
    const [open, setOpen] = useState(false)
    const [pollId, setPollId] = useState(0)

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

        return () => cleanup()

    },[props.match.params.id])

    const handleOpenClose = (pollId) =>{
        setOpen(!open)
        setPollId(pollId)
    }


    return (
        <Segment style={{marginTop: '7em'}} clearing>
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
                            <Button positive
                                    disabled={poll.timeRemaining === 'PT0S'}
                                    onClick={(pollId) =>{handleOpenClose(poll.pollId)}}
                            >Add IOT Device</Button>
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
                            {`${poll.pollName}`}  {poll.pairedDevices && poll.pairedDevices.map(device => (
                            <Label basic color='red' key={device} as='a'>{device}</Label>
                        ))} </List.Content>

                    </List.Item>
                </List>
                ))
            }
            {<Modal open={open}>
                <HandleIotDevices openClose={handleOpenClose} pollId={pollId}/>
            </Modal>}

        </Segment>

    );
}

export default MyPollPage;