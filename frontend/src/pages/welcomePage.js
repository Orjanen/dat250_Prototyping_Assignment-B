import React, {Fragment} from 'react';
import IconHeader from "../components/header/IconHeader";
import {Segment, Input, Button} from 'semantic-ui-react'
import {Link} from 'react-router-dom'

const WelcomePage = () => {
    return (
        <Fragment>
            <Segment textAlign='center' style={{marginTop: '2em'}}>
                <IconHeader
                    mainText='Welcome'
                    subText='Please enter the code'
                    icon='keyboard'
                />
                <div/>
                <Input placeholder='12 34 56'/>
                <div/>
                <Button
                    primary
                    style={{marginTop: '1em'}}
                > Submit </Button>
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
