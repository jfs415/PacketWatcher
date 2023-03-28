import React, { Component, useEffect, useState } from 'react';
import { useLocalState } from "../../util/LocalStorage";
import { Button, Col, Container, Form, Row, Stack } from "react-bootstrap";


function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [jwt, setJwt] = useLocalState("", "jwt");

    function sendLoginRequest() {

        const reqBody = {
            username: username,
            password: password,
        };

        fetch("/login", {
            headers: {
                "Content-Type": "application/json"
            },
            method: "post",
            body: JSON.stringify(reqBody),
        }).then((response) => {
            if (response.ok) {
                return Promise.all([response.json(), response.headers]);
            } else {
                return Promise.reject("Invalid Login");
            }
        }).then(([body, headers]) => {
            setJwt(headers.get("authorization"));
            window.location.href = "/dashboard";
        }).catch((message) => {
            alert(message);
        });
    }

    return (
        <>
            <Form className="login-form">
                <div>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                            type="email"
                            className="creds"
                            name="username"
                            placeholder="Enter Username"
                            value={ username }
                            onKeyDown={ (e) => {
                                if (e.key === "Enter") {
                                    sendLoginRequest();
                                }
                            } }
                            onChange={ (e) => setUsername(e.target.value) }
                            required
                        />
                    </Form.Group><br/>
                    
                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            className="creds"
                            name="password"
                            placeholder="Enter Password"
                            value={ password }
                            onKeyDown={ (e) => {
                                if (e.key === "Enter") {
                                    sendLoginRequest();
                                }
                            } }
                            onChange={ (e) => setPassword(e.target.value) }
                            required
                        /><br/>
                    </Form.Group><br/>

                    <Container>
                        <Row>
                            <Button variant={ "info" } id={ "submit" } type={ "button" }
                                    onClick={ () => sendLoginRequest() }>Submit</Button><br/>
                        </Row><br/>
                        <Row className="reset-pass">
                            <span><a className="password" href="/reset-password">Forgot password?</a></span>
                        </Row>
                    </Container>
                    <br/>
                </div>
            </Form>
        </>
    );
}

export default Login;