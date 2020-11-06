import React from 'react';
import {Form, Select} from 'semantic-ui-react';

const SelectInput = (props) => {
    return (
        <Form.Field width={props.width}>
            <Select
                value={props.input.value}
                onChange={(event,data)=>{
                    props.input.onChange(data.value)}}
                placeholder={props.placeholder}
                options={props.options}
            />
        </Form.Field>
    );
}

export default SelectInput;