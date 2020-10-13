import React from 'react';
import { Header, Icon, Segment} from 'semantic-ui-react'

const IconHeader = (props) => {
    return (
            <Header as='h2' icon>
                <Icon name={props.icon} />
                {props.mainText}
                <Header.Subheader>
                    {props.subText}
                </Header.Subheader>
            </Header>
    );
}

export default IconHeader;
