import React, {Fragment} from 'react';
import {Segment, Input, Button} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";

const LoginPage = () => {
    return (
        <Fragment>
            <Segment textAlign='center' style={{marginTop: '2em'}}>
                <IconHeader
                    mainText='Login or Sign up'
                    subText=''
                    icon='lock'
                />
                <div/>
                <Input placeholder='Email Address'/>
                <div/>
                <Input placeholder='Password'/>
                <div/>
                    <Button primary style={{marginTop:'5px'}}>Sign up</Button>
                    <Button primary>Login</Button>
            </Segment>
        </Fragment>
    );
}

export default LoginPage;
