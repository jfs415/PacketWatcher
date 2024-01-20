import React, { useState } from 'react';
import { Button, Form } from "react-bootstrap";
import PasswordChecklist from "react-password-checklist";


function ResetPassword() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [passwordAgain, setPasswordAgain] = useState("");

    const [hasUsername, setHasUsername] = useState(false);
    const [isDisabled, setDisabled] = useState(true);
    
    const token = /[^/]*$/.exec(window.location.href)[0];

    function sendPasswordReset() {

        const reqBody = {
            username: username,
            password: password,
            token: token,
        };

        fetch("/accounts/reset", {
            headers: {
                "Content-Type": "application/json"
            },
            method: "post",
            body: JSON.stringify(reqBody),
        }).then(response => {
            if (response.ok) {
                return Promise.all([response.json(), response.headers]);
            } else {
                return Promise.reject("Invalid Login");
            }
        }).then(() => {
            window.location.href = "/";
        }).catch(message => {
            alert(message);
        });
    }

    return (
        <>
            <Form className="reset-password-form">
                <div>
                    <Form.Group controlId="formBasicUsername">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                            type="username"
                            className="creds"
                            name="username"
                            placeholder="Enter your username"
                            value={ username }
                            onKeyDown={ (e) => {
                                if (e.key === "Enter" && !isDisabled) {
                                    sendPasswordReset();
                                }
                            } }
                            onChange={ (e) => {
                                setUsername(e.target.value);
                                setHasUsername(e.target.value.length > 0);
                                setDisabled(!e.target.value.length > 0);
                            } }
                            required
                        />
                    </Form.Group><br/>
                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>New Password</Form.Label>
                        <Form.Control
                            type="password"
                            className="creds"
                            name="password"
                            placeholder="Enter new password"
                            value={ password }
                            onKeyDown={ (e) => {
                                if (e.key === "Enter" && !isDisabled) {
                                    sendPasswordReset();
                                }
                            } }
                            onChange={ (e) => setPassword(e.target.value) }
                            required
                        />
                    </Form.Group><br/>

                    <Form.Group controlId="formBasicPasswordAgain">
                        <Form.Label>Re-Enter New Password</Form.Label>
                        <Form.Control
                            type="password"
                            className="creds"
                            name="passwordAgain"
                            placeholder="Enter new password again"
                            value={ passwordAgain }
                            onKeyDown={ (e) => {
                                if (e.key === "Enter" && !isDisabled) {
                                    sendPasswordReset();
                                }
                            } }
                            onChange={ (e) => setPasswordAgain(e.target.value) }
                            required
                        /><br/>

                        <PasswordChecklist className={ "passwordChecklist" }
                                           rules={ ["minLength", "maxLength", "specialChar", "number", "capital", "match"] }
                                           minLength={ 8 }
                                           maxLength={ 20 }
                                           value={ password }
                                           valueAgain={ passwordAgain }
                                           onChange={ (isValid) => {
                                               setDisabled(!isValid && !hasUsername);
                                           } }
                        /><br/>

                        <Button disabled={ isDisabled } variant={ "info" } id={ "submit" } type={ "button" }
                                onClick={ () => sendPasswordReset() }>Submit</Button>
                    </Form.Group>
                </div>
            </Form>
        </>
    );
}

export default ResetPassword;