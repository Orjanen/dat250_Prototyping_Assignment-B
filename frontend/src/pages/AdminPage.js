import React, {Fragment} from 'react';
import {Segment, List, Button, Header} from "semantic-ui-react";
import IconHeader from "../components/header/IconHeader";

const AdminPage = () => {
    return (
        <Fragment>
        <Segment style={{marginTop: '7em'}}>
            <div style={{textAlign: "center", marginBottom: '5em'}}>
                <IconHeader
                    icon='user secret'
                    mainText='Admin Page'
                />
            </div>
            <Header style={{textAlign: "center"}}> Users</Header>
            <List divided verticalAlign='middle'>
                <List.Item>
                    <List.Content floated='right'>
                        <Button>Ban</Button>
                    </List.Content>
                    <List.Content>Lena</List.Content>
                </List.Item>
            </List>
        </Segment>

            <Segment>
                <Header style={{textAlign: "center"}}> Poll</Header>
            </Segment>
        </Fragment>
    );
}

export default AdminPage;