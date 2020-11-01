import React, {useEffect, useState} from 'react';
import { PieChart } from 'react-minimal-pie-chart';
import {Button, Grid, Segment} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";
import agent from "../api/agent";
import {Link} from "react-router-dom";



const ResultPage = (props) => {
    const [poll, setPoll] = useState({});

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

    let optionOneVotes = poll.optionOneVotes;
    let optionTwoVotes = poll.optionTwoVotes;

    const data =
        [
        { title: 'optionOne', value: optionOneVotes, color: '#2ddbc6'},
        { title: 'optionTwo', value: optionTwoVotes, color: '#d4373d' }
    ]
    return (
        <Segment style={{marginTop: '7em', textAlign: 'center'}} >
            <IconHeader
                icon='chart bar outline'
                mainText='Result'
                subText={`for pollId ${poll.pollId}`}
            />
            <Grid columns={4} textAlign='center'>
                <Grid.Column >
                    <div style={{marginTop: '2em'}} >
                        <div >
                            <h2>{poll.pollName}</h2>
                            <div>
                                <h3>
                                    {`${poll.optionOne}: ${poll.optionOneVotes}`}
                                </h3>
                            </div>
                            <div>
                                <h3>{`${poll.optionTwo}: ${poll.optionTwoVotes}`}</h3>
                            </div>
                        </div>
                    </div>
                </Grid.Column>
                <Grid.Column >
                    <div >
                        <PieChart
                            data={data}
                            label={({ dataEntry }) => dataEntry.value}
                            labelStyle={(index) => ({
                                fontSize: '5px',
                                fontFamily: 'sans-serif',
                            })}

                        />
                        </div>
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

        </Segment>
    );
}

export default ResultPage;
