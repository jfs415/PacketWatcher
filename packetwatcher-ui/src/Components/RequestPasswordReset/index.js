import React, { useState } from 'react';
import { Button, Container, Form, Row } from "react-bootstrap";
import { useNavigate } from "react-router";

function RequestPasswordReset() {

    const [email, setEmail] = useState("");
    let navigate = useNavigate();

    function Cancel() {
        navigate("/")
    }

    function SendPasswordResetRequest() {
        const reqBody = {
            email: email,
        };

        fetch("/accounts/forgot", {
            headers: {
                "Content-Type": "application/json"
            },
            method: "post",
            body: JSON.stringify(reqBody),
        }).then(response => {
            return Promise.all([response.json(), response.headers]);
        }).then(() => {
            window.location.href = "/";
        }).catch(message => {
            alert(message);
        });
    }

    return (
        <>
            <Form className="login-form">
                <div>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            className="creds"
                            name="email"
                            placeholder="Enter Email"
                            value={ email }
                            onKeyDown={ (e) => {
                                if (e.key === "Enter") {
                                    SendPasswordResetRequest();
                                }
                            } }
                            onChange={ (e) => setEmail(e.target.value) }
                            required
                        />
                    </Form.Group><br/>

                    <Container>
                        <Row>
                            <Button variant={ "secondary" } onClick={ () => Cancel() }>Cancel</Button>
                        </Row><br/>
                        <Row>
                            <Button variant={ "warning" } id={ "submit" } type={ "button" }
                                    onClick={ () => SendPasswordResetRequest() }>Continue</Button>
                        </Row>
                    </Container>
                </div>
            </Form>
        </>
    );
}

export default RequestPasswordReset;