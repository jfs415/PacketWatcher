import React from 'react';
import { useLocalState } from "../LocalStorage";
import { Navigate } from "react-router";

const PrivateRoute = ({ children }) => {

    const jwt = useLocalState("", "jwt");

    return jwt ? children : <Navigate to={ "/" }/>;
};

export default PrivateRoute;