import React, { useState } from 'react';
import Navbar from "../Navbar";

const Dashboard = () => {
    return (
        <div style={ { display: 'flex', height: '100vh', overflow: 'scroll initial' } }>
            <Navbar/>
        </div>
    );
}

export default Dashboard;