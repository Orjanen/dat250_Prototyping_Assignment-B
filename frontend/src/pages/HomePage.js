import React, {Fragment} from 'react';
import {Segment} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";

const HomePage = () => {
    return (
        <Fragment>
            <Segment textAlign='center' style={{marginTop: '2em'}}>
                <IconHeader
                    mainText='Home Page'
                    subText='Manages your polls here'
                    icon='user'
                />
            </Segment>
        </Fragment>
    );
}

export default HomePage;
