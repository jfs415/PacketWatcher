import React, { useEffect, useState } from 'react';
import Navbar from "../Navbar";
import { CDBCard, CDBCardBody, CDBDataTable } from "cdbreact";

const AuthorizationEvents = () => {
    const [events, setEvents] = useState([]);

    const data = {
        columns: [
            {
                label: "Timestamp",
                field: "timestamp",
                sort: "desc",
            },
            {
                label: "Username",
                field: "username",
            },
            {
                label: "Event Type",
                field: "eventType",
            },
            {
                label: "IP Address",
                field: "ipAddress",
            },
        ],

        rows: events,
    };

    useEffect(() => {
        fetch("/events/authorization").then(response => {
            if (response.ok) {
                return response.json();
            }

            throw response;
        }).then(data => {
            let eventsList = [];

            Object.values(data).forEach(events => {
                eventsList = events;
            });

            setEvents(eventsList);
        }).catch(error => {
            console.error(error);
        })
    }, []);

    return (
        <div style={ { display: 'flex', height: '100vh' } }>
            <Navbar/>
            <CDBCard style={ { width: "100%", height: "100%", overflowY: "auto" } }>
                <CDBCardBody style={ { paddingTop: "5%" } }>
                    <CDBDataTable className={ "data-table" }
                                  striped
                                  bordered
                                  hoveCDBDataTable
                                  entries={ 10 }
                                  pagesAmount={ 4 }
                                  data={ data }
                                  materialSearch={ true }
                                  noBottomColumns={ true }
                                  searching={ true }
                    />
                </CDBCardBody>
            </CDBCard>
        </div>
    );
};

export default AuthorizationEvents;