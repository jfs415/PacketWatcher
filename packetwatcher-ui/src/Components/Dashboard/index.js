import React, { useState } from 'react';
import { useLocalState } from "../../util/LocalStorage";
import Navbar from "../Navbar";

const Dashboard = () => {

    const [jwt, setJwt] = useLocalState("", "jwt");

    return (
        <div style={ { display: 'flex', height: '100vh', overflow: 'scroll initial' } }>
            <Navbar/>
        </div>
    );
}

export default Dashboard;