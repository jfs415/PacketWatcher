import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'; //Order matters, always ensure custom css is imported last
import { useLocalState } from "./util/LocalStorage";
import { Route, Routes } from "react-router";
import Login from "./Components/Login";
import Dashboard from "./Components/Dashboard";
import PrivateRoute from "./util/PrivateRoute";
import ResetPassword from "./Components/ResetPassword";
import RequestPasswordReset from "./Components/RequestPasswordReset";

function App() {
    const [jwt, setJwt] = useLocalState("", "jwt")

    return (
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
        </Routes>
    );
}

export default App;
