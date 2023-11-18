import React from 'react';
import Navbar from "../Navbar";
import {CDBCard} from "cdbreact";

const SystemAnalytics = () => {
    return (
        <div style={ { display: 'flex', height: '100vh', overflow: 'scroll initial', background: "#242526" } }>
            <Navbar/>
            <CDBCard id={"data-card"}>
            </CDBCard>
        </div>
    );
};

export default SystemAnalytics;