import React, {Fragment, useState} from 'react';
import IconHeader from "../components/header/IconHeader";
import {Segment, Button, Input} from 'semantic-ui-react'
import {Link} from 'react-router-dom'
import agent from "../api/agent";

const WelcomePage = (props) => {
    const [pollId, setPollId] = useState('');
    const [showError, setShowError] = useState(false);

    const error = (<p style={{color: 'red'}}> Cant find a Poll with this Id </p>)

    const getPoll = async () =>{

        let poll = null
        try {
            poll = await agent.Poll.details(pollId)
        }catch (e){
            console.log('getPoll failed')
        }

        if (poll === null){
            setShowError(true)
        }else {
            props.history.push(`/poll/${pollId}`)
        }

    }

    return (
        <Fragment>
            <Segment textAlign='center' style={{marginTop: '2em'}}>
                <IconHeader
                    mainText='Welcome'
                    subText='Please enter the code'
                    icon='keyboard'
                />
                <br/>
                {showError && error}
                <Input
                placeholder='12 34 56'
                onChange={event => setPollId(event.target.value)}
                />
                <div style={{marginTop: '5px'}}>
                    <Button
                        onClick={() => getPoll()}
                        primary
                    > Submit</Button>

                </div>

                <h3>Or</h3>

                <Button
                    as={Link}
                    to='/login'
                    primary
                > Login/Sign up </Button>

            </Segment>
        </Fragment>
    );
}

export default WelcomePage;
