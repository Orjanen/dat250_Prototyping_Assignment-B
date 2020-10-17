import React from 'react';
import agent from "../api/agent";
import {Button, Form, Segment} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";
import {Field, Form as FinalForm} from "react-final-form";
import TextInput from "../shared/Form/TextInput";

const CreatePollPage = (props) => {

    const handleFinalFormSubmit = async (values) => {
        const userId  = window.localStorage.getItem('userId');
        try {
            await agent.Poll.create(userId, values).then(res =>{
                props.history.push(`/poll/${res.pollId}`)
            })
        }catch (e){
            console.log(e)
        }
    }

    return (
        <Segment style={{marginTop: '3em'}} textAlign='center'>
            <IconHeader
                icon='wordpress forms'
                mainText='Create new poll'
            />
            <FinalForm
                onSubmit={handleFinalFormSubmit}
                render={({handleSubmit}) =>(
                    <Form onSubmit={handleSubmit}>
                        <Field
                            name='pollName'
                            placeholder='Poll Name'
                            value={'name'}
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
                floated='right'
                content='Cancel'
                color='grey'
            />
        </Segment>
    );
}

export default CreatePollPage;
