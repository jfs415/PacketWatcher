import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'; //Order matters, always ensure custom css is imported last
import { Route, Routes } from "react-router";
import Login from "./Components/Login";
import Dashboard from "./Components/Dashboard";
import PrivateRoute from "./util/PrivateRoute";
import ResetPassword from "./Components/ResetPassword";
import RequestPasswordReset from "./Components/RequestPasswordReset";
import Packets from "./Components/Packets";
import SystemAnalytics from "./Components/SystemAnalytics";
import SystemSettings from "./Components/SystemSettings";
import UserProfile from "./Components/UserProfile";
import Users from "./Components/Users";
import AuthenticationEvents from "./Components/AuthenticationEvents";
import AuthorizationEvents from "./Components/AuthorizationEvents";
import LockedUserHistory from "./Components/LockedUserHistory";
import Logout from "./Components/Logout";
import Console from "./Components/Console";
import Stats from "./Components/Stats";
import {useState} from "react";
import { Tooltip } from 'react-tooltip';

function App() {
    const [content, setContent] = useState("");
    
    return (
        <Routes>
            <Route path="/" element={<Login/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/logout" element={<Logout/>}/>
            <Route path="/reset-password" element={<RequestPasswordReset/>}/>
            <Route path="/accounts/reset/*" element={<ResetPassword/>}/>
            <Route
                path="/dashboard"
                element={
                    <PrivateRoute>
                        <Dashboard setTooltipContent={setContent} />
                        <Tooltip content={content}/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/packets"
                element={
                    <PrivateRoute>
                        <Packets/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/system/analytics"
                element={
                    <PrivateRoute>
                        <SystemAnalytics/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/system/settings"
                element={
                    <PrivateRoute>
                        <SystemSettings/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/profile"
                element={
                    <PrivateRoute>
                        <UserProfile/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/users"
                element={
                    <PrivateRoute>
                        <Users/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/events/authentication"
                element={
                    <PrivateRoute>
                        <AuthenticationEvents/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/events/authorization"
                element={
                    <PrivateRoute>
                        <AuthorizationEvents/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/users/locked/history"
                element={
                    <PrivateRoute>
                        <LockedUserHistory/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/stats/dashboard"
                element={
                    <PrivateRoute>
                        <Stats/>
                    </PrivateRoute>
                }
            />
            <Route
                path="/console"
                element={
                    <PrivateRoute>
                        <Console/>
                    </PrivateRoute>
                }
            />
        </Routes>
    );
}

export default App;
