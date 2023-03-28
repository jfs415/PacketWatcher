import React, { useState } from 'react';
import { useLocalState } from "../../util/LocalStorage";
import Navbar from "../Navbar";

const Dashboard = ({ children }) => {

    const [jwt, setJwt] = useLocalState("", "jwt");

    return (
        <>
            <Navbar />
        </>
    );
}

export default Dashboard;