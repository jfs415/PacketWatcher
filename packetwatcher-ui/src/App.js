import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'; //Order matters, always ensure custom css is imported last
import { useLocalState } from "./util/LocalStorage";
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

function App() {
    const [jwt, setJwt] = useLocalState("", "jwt")

    return (
        <>
            <Routes>
                <Route path="/" element={ <Login/> }/>
                <Route path="/login" element={ <Login/> }/>
                <Route path="/reset-password" element={ <RequestPasswordReset/> }/>
                <Route path="/accounts/reset/*" element={ <ResetPassword/> }/>
                <Route
                    path="/dashboard"
                    element={
                        <PrivateRoute>
                            <Dashboard/>
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
            </Routes>
        </>
    );
}

export default App;
