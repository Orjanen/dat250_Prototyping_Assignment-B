import React, {Fragment} from 'react';
import {Segment,Button, Form, Label} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";
import {Link, withRouter} from "react-router-dom";
import {Field, Form as FinalForm} from "react-final-form";
import TextInput from "../shared/Form/TextInput";
import agent from "../api/agent";


const LoginPage = (props) => {

    const handleFinalFormSubmit = async (values) => {
        try {
            await agent.User.login(values)
        }catch (e){
            console.log(e + 'Login failed')
        }
            if (props.isOnPollPage){
                window.location.reload()
            }else {
                props.history.push(`/homepage/${window.localStorage.getItem('userId')}`)
            }
    }

    return (
        <Fragment>
            <Segment style={{marginTop: '2em'}} clearing>
                <div style={{textAlign:'center'}}>
                <IconHeader
                    mainText='Login or Sign up'
                    subText=''
                    icon='lock'
                />
                </div>
                <FinalForm
                    onSubmit={handleFinalFormSubmit}
                    render={({handleSubmit}) =>(
                        <Form onSubmit={handleSubmit}>
                            <Label basic>Email</Label>
                            <Field
                                name='email'
                                placeholder='Email'
                                component={TextInput}
                            />
                            <Label basic>Password</Label>
                            <Field
                                name='password'
                                placeholder='Password'
                                component={TextInput}
                                type='password'
                            />
                            <Button
                                type='submit'
                                content='Login'
                                color = 'green'
                                floated='right'
                            />
                        </Form>
                    )}
                />
                    <Button primary
                            as={Link}
                            to='/signup'
                            color='blue'
                            floated='right'

                    >Sign up</Button>
            </Segment>
        </Fragment>
    );
}

export default withRouter(LoginPage);
