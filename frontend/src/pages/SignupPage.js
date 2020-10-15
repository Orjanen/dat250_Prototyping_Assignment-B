import React from 'react';
import {Segment, Form, Button, Label} from "semantic-ui-react";
import {Form as FinalForm, Field} from 'react-final-form'
import {Link} from "react-router-dom";

import TextInput from "../shared/Form/TextInput";
import IconHeader from "../components/header/IconHeader";


const SignupPage = () => {

    const handleFinalFormSubmit = async (values) => {
        console.log(values)
        try {
        }catch (e){
            console.log(e)
        }
    }

    return (
        <Segment style={{marginTop: '3em'}}>
            <div style={{textAlign:'center'}}>
            <IconHeader
                icon='wordpress forms'
                mainText='Signup'
            />
            </div>
            <FinalForm
                onSubmit={handleFinalFormSubmit}
                render={({handleSubmit}) =>(
                    <Form onSubmit={handleSubmit}>
                        <Label basic>Username</Label>
                        <Field
                            name='userName'
                            placeholder='UserName'
                            component={TextInput}
                        />
                        <Label basic>Email</Label>
                        <Field
                            name='email'
                            placeholder='Email Address'
                            component={TextInput}
                        />
                        <Label basic>Password</Label>
                        <Field
                            name='password'
                            placeholder='Password'
                            type='password'
                            component={TextInput}
                        />
                        <Label basic>Confirm Password</Label>
                        <Field
                            name='confirmPassword'
                            placeholder='Confirm Password'
                            type='password'
                            component={TextInput}
                        />

                        <Button
                            positive
                            floated='right'
                            type='submit'
                            content='Submit'
                            color='green'
                        />
                    </Form>
                )}
            />

            <Button
                as={Link}
                to='/'
                floated='right'
                content='Cancel'
                color='grey'
            />
        </Segment>
    );
}

export default SignupPage;