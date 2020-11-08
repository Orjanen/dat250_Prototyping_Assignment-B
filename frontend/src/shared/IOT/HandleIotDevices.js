import React, {useEffect, useState} from 'react';
import {Button, Form, Label, Segment} from "semantic-ui-react";
import SelectInput from "../Form/SelectInput";
import IconHeader from "../../components/header/IconHeader";
import {Field, Form as FinalForm} from "react-final-form";
import agent from "../../api/agent";
import {cleanup} from "@testing-library/react";


const HandleIotDevices = (props) => {

    const [devices, setDevices] = useState([]);

    useEffect( () =>{
        const getAllUnpairedDevices = async () =>{
            try {
                await  agent.IOT.getAllUnpairedDevices().then(response => {
                    setDevices(response)
                })
            }catch (e){
                console.log('Some thing went wrong')
            }
        }
        getAllUnpairedDevices()

        return () => cleanup()

    },[devices])

    const IotOptions = devices.map((opt, index) => ({
        key: opt.publicDeviceId,
        text: opt.publicDeviceId,
        value: opt.publicDeviceId,

    }))

    const handleFinalFormSubmit = async (values) => {
        try {
            await agent.IOT.pairPollAndDevice(values.publicDeviceId, props.pollId)
        }catch (e){
            console.log('Pairing failed')
        }
        props.openClose()
    }
    return (
        <Segment style={{marginTop: '3em'}} clearing>
            <div style={{textAlign:'center'}}>
                <IconHeader
                    icon='wordpress forms'
                    mainText='Add Iot Device to poll'
                />
            </div>
            <FinalForm
                onSubmit={handleFinalFormSubmit}
                render={({handleSubmit}) =>(
                    <Form onSubmit={handleSubmit}>
                        <Label basic>IOT Device</Label>
                        <Field
                            name='publicDeviceId'
                            placeholder='Add IOT Device'
                            value={props.pollId}
                            component={SelectInput}
                            options={IotOptions}
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
                onClick={props.openClose}
                floated='right'
                content='Cancel'
                color='grey'
            />
        </Segment>
    );
}

export default HandleIotDevices;