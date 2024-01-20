import React, {useEffect, useState} from 'react';
import Navbar from "../Navbar";
import {CDBBtn, CDBCard, CDBInput} from "cdbreact";
import "./styles.css"
import {CardHeader, Row} from "react-bootstrap";
import {getFromLocalStorage} from "../../util/LocalStorage";


function validateField(field) {
    return field === null ? "" : field;
}

const UserProfile = () => {
    const [userProfile, setUserProfile] = useState("");

    useEffect(() => {
        fetch(`/profile?token=${getFromLocalStorage("jwt")}`).then(response => {
            if (response.ok) {
                return response.json();
            }

            throw response;
        }).then(data => {
            console.log(data)
            setUserProfile(data);
        });
    }, []);

    function updateProfile(field, value) {
        userProfile[field] = value;
    }

    function requestPasswordReset() {
        
        const emailRequest = {
            email: userProfile['email']
        }
        
        fetch("/accounts/forgot", {
            headers: {
                "Content-Type": "application/json"
            },
            method: "post",
            body: JSON.stringify(emailRequest),
        }).then(response => {
            return Promise.all([response.json(), response.headers]);
        }).then(() => {
            alert("A password reset link has been sent to your email.")
        }).catch(message => {
            alert(message);
        });
    }

    function submitProfileChange() {
        fetch("/profile/update", {
            headers: {
                "Content-Type": "application/json"
            },
            method: "put",
            body: JSON.stringify(userProfile),
        }).then((response) => {
            if (response.ok) {
                alert("Profile Updated Successfully")
                return Promise.all([response.json(), response.headers]);
            } else {
                return Promise.reject("Unable to update profile");
            }
        }).then(([body, headers]) => {
            window.location.href = "/profile";
        }).catch((message) => {
            alert(message);
        });
    }

    return (
        <div style={{display: 'flex'}}>
            <Navbar/>
            <CDBCard id={"data-card"}>
                <CDBCard className={"profile-card"} style={{border: 0, background: "#242526"}}>
                    <h1 className={"profile-header"}>Your PacketWatcher Profile</h1>

                    <div className={"profile-field"}>
                        <CardHeader>Username</CardHeader>
                        <CDBInput type="text" className={"profile-input"}
                                  placeholder={validateField(userProfile['username'])} disabled={true}/>
                    </div>

                    <div className={"profile-field"}>
                        <CardHeader>First Name</CardHeader>
                        <CDBInput type="text" className={"profile-input"}
                                  getValue={value => updateProfile("firstName", value)}
                                  placeholder={validateField(userProfile['firstName'])}/>
                    </div>

                    <div className={"profile-field"}>
                        <CardHeader>Last Name</CardHeader>
                        <CDBInput type="text" className={"profile-input"}
                                  getValue={value => updateProfile("lastName", value)}
                                  placeholder={validateField(userProfile['lastName'])}/>
                    </div>

                    <div className={"profile-field"}>
                        <CardHeader>Email Address</CardHeader>
                        <CDBInput type="text" className={"profile-input"}
                                  getValue={value => updateProfile("email", value)}
                                  placeholder={validateField(userProfile['email'])}/>
                    </div>


                    <div className={"profile-field"}>
                        <CardHeader>Phone Number</CardHeader>
                        <CDBInput type="text" className={"profile-input"}
                                  getValue={value => updateProfile("phone", value)}
                                  placeholder={validateField(userProfile['phone'])}/>
                    </div>

                    <div className={"profile-field"}>
                        <CardHeader>User Level</CardHeader>
                        <CDBInput type="text" className={"profile-input"}
                                  getValue={value => updateProfile("level", value)}
                                  placeholder={validateField(userProfile['level'])} disabled={true}/>
                    </div>

                    <CDBBtn style={{
                        verticalAlign: "center",
                        display: "block",
                        marginLeft: "auto",
                        marginRight: "auto",
                        marginTop: "3%"
                    }} onClick={() => submitProfileChange()} color={"primary"} circle outline>Update Profile</CDBBtn>

                    <CDBBtn style={{
                        verticalAlign: "center",
                        display: "block",
                        marginLeft: "auto",
                        marginRight: "auto",
                        marginTop: "3%"
                    }} onClick={() => requestPasswordReset()} color={"primary"} circle outline>Reset Password</CDBBtn>
                </CDBCard>
            </CDBCard>
        </div>
    );
};

export default UserProfile;