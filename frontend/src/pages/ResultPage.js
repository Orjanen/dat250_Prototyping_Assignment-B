import React, {useCallback, useEffect, useState, Fragment} from 'react';
import { PieChart } from 'react-minimal-pie-chart';
import {Button, Grid, Modal, Segment} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";
import agent from "../api/agent";
import {Link} from "react-router-dom";
import Stomp from "stomp-websocket";
import LoginPage from "./LoginPage";



const ResultPage = (props) => {

    const [poll, setPoll] = useState({});
    const [optionOne, setOptionOne] = useState(0)
    const [optionTwo, setOptionTwo] = useState(0)
    const logedIn = (window.localStorage.getItem('token') !== null)

    const updateVotes = useCallback((opt1, opt2) => {
        setOptionOne(oldOptionOneVotes => oldOptionOneVotes + opt1)
        setOptionTwo(oldOptionTwoVotes => oldOptionTwoVotes + opt2)
    }, [])

    const setVotes = useCallback((opt1, opt2) => {
        setOptionOne(opt1)
        setOptionTwo(opt2)
    })


    useEffect( () =>{
        const getPoll = async (id) =>{
            try {
                await  agent.Poll.details(id).then(response => {
                    setPoll(response)
                })
            }catch (e){
                console.log('Some thing went wrong')
            }
        }
        getPoll(props.match.params.pollId)

    },[props.match.params.pollId])




    useEffect(() => {

        if(poll.pollId === undefined) return

        //On first connect or re-connect: set votes to the poll's total votes
        setVotes(poll.optionOneVotes, poll.optionTwoVotes)

        let connection = new WebSocket("ws://localhost:8080/ws/websocket")
        let ws = Stomp.over(connection);

        let headers = {

            "Authorization": window.localStorage.getItem("token")
        }


        ws.connect(headers, function (frame) {

            ws.subscribe("/topic/poll/" +poll.pollId,
                (message) => {

                    const vote = JSON.parse(message.body)

                    updateVotes(vote.optionOneVotes, vote.optionTwoVotes)
                }

            )
        }, )

    }, [poll, updateVotes])

    let optionOneVotes = optionOne;
    let optionTwoVotes = optionTwo;

    const data =
        [
        { title: 'optionOne', value: optionOneVotes, color: '#2ddbc6', text: poll.optionOne},
        { title: 'optionTwo', value: optionTwoVotes, color: '#d4373d', text: poll.optionTwo }
    ]

    let show;

    if (poll.private && logedIn){
        show = true
    } else if(poll.private && !logedIn){
        show = false
    } else if(poll.private){
        show = true
    }

    if (poll.private === false){
        show = true
    }

    const modalLogin = (
        <Modal
            open={!show}
        >
            <LoginPage isOnPollPage={true} pollId={props.match.params.pollId}/>
        </Modal>
    )

    const loginMassage = (
            <Segment style={{marginTop: '7em', textAlign: 'center'}}>
                <h2 style={{color: "red", textAlign: "center"}}>
                    You need to be logged in to see this poll
                </h2>
            </Segment>

    )


    return (
        <Fragment>
            {show ? <Segment style={{marginTop: '7em', textAlign: 'center'}}>
                <IconHeader
                    icon='chart bar outline'
                    mainText='Result'
                    subText={`for pollId ${poll.pollId}`}
                />
                <Grid columns={4} textAlign='center' style={{marginBottom:'2em'}}>
                    <Grid.Column>
                        <div style={{marginTop: '2em'}}>
                            <div>
                                <h2>{poll.pollName}</h2>
                                <div>
                                    <h3>
                                        {`${poll.optionOne}: ${optionOne}`}
                                    </h3>
                                </div>
                                <div>
                                    <h3>{`${poll.optionTwo}: ${optionTwo}`}</h3>
                                </div>
                            </div>
                        </div>
                    </Grid.Column>
                    <Grid.Column>
                            <PieChart
                                data={data}
                                label={({dataEntry}) => dataEntry.text}
                                labelStyle={(index) => ({
                                    fontSize: '5px',
                                    fontFamily: 'sans-serif',
                                })}
                            />
                    </Grid.Column>
                </Grid>

                <Button
                    as={Link}
                    to={`/poll/${poll.pollId}`}
                    color='blue'
                    content='Vote Page'
                />
                {window.localStorage.getItem('token') &&
                <Button
                    as={Link}
                    to={`/homepage/${window.localStorage.getItem('userId')}`}
                    color='green'
                    content='Home Page'
                />
                }

            </Segment> : loginMassage}
            {modalLogin}
        </Fragment>
    );
}

export default ResultPage;
