import React, {Fragment} from 'react';
import {Segment,Button, Form, Label} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";
import {Link} from "react-router-dom";
import {Field, Form as FinalForm} from "react-final-form";
import TextInput from "../shared/Form/TextInput";
import agent from "../api/agent";


const LoginPage = (props) => {


    const handleFinalFormSubmit = async (values) => {
        console.log(values)
        try {
            await agent.User.login(values).then(res => console.log(res))

        }catch (e){

        }
        //props.history.push('/homepage/123')
    }

    return (
        <Fragment>
            <Segment style={{marginTop: '2em'}}>
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
                            <Label basic>Last name</Label>
                            <Field
                                name='password'
                                placeholder='Last Name'
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

export default LoginPage;
