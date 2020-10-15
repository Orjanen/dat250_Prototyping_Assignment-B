import React from 'react';

import {Form, Label} from 'semantic-ui-react'

const TextInput = (props) => {
    return (
       <Form.Field error={props.touched && !!props.error}  type={props.type} width={props.width}>
            <input
                {...props.input}
                placeholder={props.placeholder}/>
           {props.touched && props.error && (
               <Label basic color='red'>{props.error}</Label>
           )}
       </Form.Field>
    );
}

export default TextInput;