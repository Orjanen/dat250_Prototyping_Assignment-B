import React, {useState} from 'react';
import agent from "../api/agent";
import {Link} from "react-router-dom";
import {Button, Form, Segment, Checkbox} from "semantic-ui-react";
import {Field, Form as FinalForm} from "react-final-form";
import DateTimePicker from 'react-datetime-picker';

import IconHeader from "../components/header/IconHeader";
import TextInput from "../shared/Form/TextInput";

const CreatePollPage = (props) => {

    const [value, onChange] = useState(new Date());
    const [isPrivate, setIsPrivate] = useState(false)

    const changePrivacy = () =>{
        setIsPrivate(!isPrivate)
    }


    const handleFinalFormSubmit = async (values) => {
        const userId  = window.localStorage.getItem('userId');
        values.isPrivate = isPrivate
        values.duration = Math.abs((value - Date.now())/1000)
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
                        <Field
                            name='optionOne'
                            placeholder='Option 1'
                            value={'optionOne'}
                            component={TextInput}
                        />
                        <Field
                            name='optionTwo'
                            placeholder='Option 2'
                            value={'optionTwo'}
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
                <span>Pick a closing time for the poll:   </span>
                <DateTimePicker
                    onChange={onChange}
                    value={value}
                />
                <Checkbox
                    onClick={() => changePrivacy()}
                    style={{marginLeft: '40px'}}
                    label='Make the poll private'></Checkbox>


            <Button
                as={Link}
                to={`/homepage/${window.localStorage.getItem('userId')}`}
                floated='right'
                content='Cancel'
                color='grey'
            />
        </Segment>
    );
}

export default CreatePollPage;
