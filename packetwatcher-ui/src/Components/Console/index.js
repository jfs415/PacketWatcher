import React, {useEffect} from "react";
import Navbar from "../Navbar";
import {CDBBtn, CDBCard, CDBCardBody, CDBInput} from "cdbreact";
import "./styles.css"


const Console = () => {
    // useEffect(() => {
    //     fetch("/events/authorization").then(response => {
    //         if (response.ok) {
    //             return response.json();
    //         }
    //
    //         throw response;
    //     }).then(data => {
    //         let eventsList = [];
    //
    //         Object.values(data).forEach(events => {
    //             eventsList = events;
    //         });
    //
    //         setEvents(eventsList);
    //     }).catch(error => {
    //         console.error(error);
    //     })
    // }, []);

    return (
        <div style={{display: 'flex', height: '100vh'}}>
            <Navbar/>
            <CDBCard id={"data-card"}>
                <CDBCardBody style={{padding: "10%"}}>
                    <div className={"console-div"}>
                        {/*<CDBInput size={"lg"} id={"console-log"} type="textarea" disabled={true}/>*/}
                        <textarea id={"console-log"} placeholder={"affsdsdsfd"} rows={28} disabled={true}></textarea>
                        <CDBInput type="text" icon={"terminal"}/>
                    </div>
                    <CDBBtn style={{
                        verticalAlign: "center",
                        display: "block",
                        marginLeft: "auto",
                        marginRight: "auto",
                        marginTop: "3%"
                    }} color={"primary"} circle outline>Submit</CDBBtn>
                </CDBCardBody>
            </CDBCard>
        </div>
    );
};

export default Console;