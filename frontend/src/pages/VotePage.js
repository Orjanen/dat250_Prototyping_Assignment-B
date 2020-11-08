import React, {Fragment, useEffect, useState} from 'react';
import {Button, Checkbox, Grid, Segment, Modal} from "semantic-ui-react";

import agent from "../api/agent";
import IconHeader from "../components/header/IconHeader";
import CountDownTimer from "../shared/Timer/CountDownTimer";
import {Link, withRouter} from "react-router-dom";
import LoginPage from "./LoginPage";

const VotePage = (props) => {

    const [poll, setPoll] = useState({});
    const [opt1, setOpt1] = useState(false)
    const [opt2, setOpt2] = useState(false)

    const logedIn = (window.localStorage.getItem('token') !== null)

    useEffect(() => {
        const getPoll = async (id) => {
            try {
                await agent.Poll.details(id).then(response => {
                    setPoll(response)
                })
            } catch (e) {
                console.log('Some thing went wrong')
            }
        }
        getPoll(props.match.params.pollId)

    }, [props.match.params.pollId])

    const option1 = {
        option1Count: 1,
        option2Count: 0
    }

    const option2 = {
        option1Count: 0,
        option2Count: 1
    }


    const votehandler = async () => {
        console.log('votehandler was called')
        if (opt1) {
            try {
                await agent.Vote.VoteAsRegisteredUser(poll.pollId, window.localStorage.getItem('userId'), option1)
            } catch (e) {
                console.log(e, 'cant send the vote')
            }
        } else {
            try {
                await agent.Vote.VoteAsRegisteredUser(poll.pollId, window.localStorage.getItem('userId'), option2)
            } catch (e) {
                console.log(e, 'cant send the vote')
            }
        }
    }

    const addVoteByUnregisteredUserToPoll = async () => {
        console.log('addVoteByUnregisteredUserToPoll was called')
        if (opt1) {
            try {
                await agent.Vote.addVoteByUnregisteredUserToPoll(poll.pollId, option1)
            } catch (e) {
                console.log(e, 'cant send the vote')
            }
        } else {
            try {
                await agent.Vote.addVoteByUnregisteredUserToPoll(poll.pollId, option2)
            } catch (e) {
                console.log(e, 'cant send the vote')
            }
        }
    }

    const loginMassage = (
        <Fragment>
            <Segment style={{marginTop: '7em', textAlign: 'center'}}>
                <h2 style={{color: "red", textAlign: "center"}}>
                    You need to be logged in to see this poll
                </h2>
            </Segment>
        </Fragment>
    )

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

    return (
        <Fragment>
            {show ? <Segment style={{marginTop: '7em', textAlign: 'center'}}>
                <IconHeader
                    icon='pie graph'
                    mainText='Vote'
                />
                <Grid columns={4} textAlign='center'>
                    <Grid.Column>
                        <div>
                            <h2>{poll.pollName}</h2>
                            <div>
                                <h3>
                                    {poll.optionOne}
                                    <Checkbox
                                        disabled={opt2}
                                        style={{marginLeft: '20px'}}
                                        checked={opt1}
                                        onChange={(e, data) => setOpt1(data.checked)}
                                    />
                                </h3>
                            </div>
                            <div>
                                <h3>{poll.optionTwo}
                                    <Checkbox
                                        disabled={opt1}
                                        style={{marginLeft: '20px'}}
                                        checked={opt2}
                                        onChange={(e, data) => setOpt2(data.checked)}
                                    />
                                </h3>
                            </div>
                        </div>
                    </Grid.Column>
                    <Grid.Column>
                        <h3>Vote closes in:</h3>
                        <CountDownTimer time={poll.timeRemaining}/>
                    </Grid.Column>
                </Grid>
                <Button
                    as={Link}
                    to={`/poll/${poll.pollId}/result`}
                    onClick={logedIn ? votehandler: addVoteByUnregisteredUserToPoll}
                    disabled={poll.timeRemaining === 'PT0S' || !opt1 && !opt2}
                    style={{marginTop: '2em', textAlign: 'center'}}
                    positive
                    content='Vote'
                />
                <Button
                    as={Link}
                    to={`/poll/${poll.pollId}/result`}
                    color='blue'
                    content='Result'
                />
            </Segment> : loginMassage}
            {modalLogin}
        </Fragment>
    );
}

export default withRouter(VotePage);