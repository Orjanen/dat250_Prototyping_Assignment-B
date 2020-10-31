import React, {useEffect, useState} from 'react';
import {Segment, Button, Checkbox, Grid} from "semantic-ui-react";

import agent from "../api/agent";
import IconHeader from "../components/header/IconHeader";
import CountDownTimer from "../shared/Timer/CountDownTimer";

const VotePage = (props) => {

    const [poll, setPoll] = useState({});
    const [opt1, setOpt1] = useState(false)
    const [opt2, setOpt2] = useState(false)

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


    const votehandler = () =>{
        if (opt1){
            console.log({
                optionOne: 1,
                optionTwo: 0
            })
        }else {
            console.log({
                optionOne: 0,
                optionTwo: 1
            })
        }
    }
    let duration ={
        hours: 0,
        minutes: 0,
        seconds: 0,
    }

    poll.timeRemaining && (duration.hours = poll.timeRemaining.split('PT')[1].split('H')[0])
    poll.timeRemaining && (duration.minutes = poll.timeRemaining.split('PT')[1].split('H')[1].split('M')[0])
    poll.timeRemaining && (duration.seconds = poll.timeRemaining.split('PT')[1].split('H')[1].split('M')[1]
        .split('.')[0])

    let time  = (duration.hours * (60000 * 60) + duration.minutes * 60000 + duration.seconds * 1000)

    time = new Date().getMilliseconds() + Date.now() +time

    return (
        <Segment style={{marginTop: '7em', textAlign: 'center'}} >
            <IconHeader
                icon='pie graph'
                mainText='Vote'
            />
            <Grid columns={4} textAlign='center'>
                <Grid.Column>
                    <div >
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
                    <CountDownTimer time={time && time}/>

                </Grid.Column>

            </Grid>
                <Button
                    onClick={votehandler}
                    disabled={!opt1 && !opt2}
                    style={{marginTop: '2em', textAlign: 'center'}}
                    positive
                    content='Vote'
                />
        </Segment>
    );
}

export default VotePage;