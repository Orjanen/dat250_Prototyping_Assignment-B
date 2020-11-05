import React, {useEffect, useState} from 'react';
import {Button, Form, Label, Segment} from "semantic-ui-react";
import {Field, Form as FinalForm} from "react-final-form";
import TextInput from "../shared/Form/TextInput";
import {Link} from "react-router-dom";

import agent from "../api/agent";
import IconHeader from "../components/header/IconHeader";

const ProfileDetails = (props) => {


    const [user, setUser] = useState({})

    useEffect( () =>{
        const getUser = async (id) =>{
            try {
                await  agent.User.details(id).then(response => {
                    setUser(response)
                })
            }catch (e){
                console.log('Some thing went wrong')
            }
        }
        getUser(props.match.params.id)

    },[props.match.params.id])

    const handleFinalFormSubmit = async (values) => {
        try {
            await agent.User.update(values, props.match.params.id)

            props.history.push(`/homepage/${window.localStorage.getItem('userId')}`)

        }catch (e){
            console.log('Update user failed')
        }
    }

    return (
        <Segment style={{marginTop: '3em'}}>
            <div style={{textAlign:'center'}}>
                <IconHeader
                    icon='wordpress forms'
                    mainText='Edit User Details'
                    subText={`for user ${user.firstName && user.firstName} ${user.lastName && user.lastName}`}
                />
            </div>
            <FinalForm
                onSubmit={handleFinalFormSubmit}
                initialValues={user}
                render={({handleSubmit}) =>(
                    <Form onSubmit={handleSubmit}>
                        <Label basic>First name</Label>
                        <Field
                            name='firstName'
                            placeholder='First Name'
                            value={'firstname'}
                            component={TextInput}
                        />
                        <Label basic>Last name</Label>
                        <Field
                            name='lastName'
                            placeholder='Last Name'
                            component={TextInput}
                        />
                        <Label basic>Email</Label>
                        <Field
                            name='email'
                            placeholder='Email Address'
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
                to={`/homepage/${props.match.params.id}`}
                floated='right'
                content='Cancel'
                color='grey'
            />
        </Segment>
    );
}

export default ProfileDetails;
