import React, {Fragment, useState} from 'react';
import IconHeader from "../components/header/IconHeader";
import {Segment, Button, Input} from 'semantic-ui-react'
import {Link} from 'react-router-dom'

const WelcomePage = () => {
    const [pollId, setPollId] = useState('');

    return (
        <Fragment>
            <Segment textAlign='center' style={{marginTop: '2em'}}>
                <IconHeader
                    mainText='Welcome'
                    subText='Please enter the code'
                    icon='keyboard'
                />
                <br/>
                <Input
                placeholder='12 34 56'
                onChange={event => setPollId(event.target.value)}
                />
                <div style={{marginTop: '5px'}}>
                    <Button
                        as={Link}
                        to={`poll/${pollId}`}
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
