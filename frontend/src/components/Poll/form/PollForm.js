import React from 'react';
import {Segment, Form, Button} from "semantic-ui-react";
import IconHeader from "../../header/IconHeader";
import {Form as FinalForm, Field} from 'react-final-form'
import TextInput from "../../../shared/Form/TextInput";

import agent from '../../../api/agent'

const PollForm = () => {

    const handleFinalFormSubmit = async (values) => {
        console.log(values)
        try {
            await agent.Poll.create('tr8Au8WS6YAkiKqv1c99gpQs4NX02Q', values)
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

export default PollForm;