import React, {Fragment} from 'react';
import {Segment, Item, Card} from "semantic-ui-react";
import {Link} from 'react-router-dom'
import IconHeader from "../components/header/IconHeader";

const HomePage = () => {
    return (
        <Fragment>
            <Segment textAlign='center' style={{marginTop: '2em'}}>
                <IconHeader
                    mainText='Home Page'
                    subText='Manages stuff here'
                    icon='user'
                />

                <Item.Group>
                    <Item>
                        <Card centered>
                        <Item.Content
                            content='Create Poll'
                            as={Link} to='/createnewpoll'
                            verticalAlign='middle'/>
                        </Card>
                    </Item>

                    <Item>
                        <Card centered>
                            <Item.Content
                                as={Link} to='/profiledetails'
                                content='Edit user details'
                                verticalAlign='middle'/>
                        </Card>
                    </Item>

                    <Item>
                        <Card centered>
                            <Item.Content
                                content='My polls'
                                verticalAlign='middle'/>
                        </Card>
                    </Item>
                </Item.Group>

            </Segment>
        </Fragment>
    );
}

export default HomePage;
