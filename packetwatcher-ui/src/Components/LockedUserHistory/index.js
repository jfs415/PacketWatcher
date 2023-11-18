import React, { useEffect, useState } from 'react';
import Navbar from "../Navbar";
import { CDBCard, CDBCardBody, CDBDataTable } from "cdbreact";

const LockedUserHistory = () => {
    const [lockedUserHistory, setLockedUSerHistory] = useState([]);

    const data = {
        columns: [
            {
                label: "Username",
                field: "username",
                sort: "desc",
            },
            {
                label: "First Locked",
                field: "firstLocked",
            },
            {
                label: "Last Locked",
                field: "lastLocked",
            },
            {
                label: "Number of Times Locked",
                field: "numberOfTimesLocked",
            },
        ],

        rows: lockedUserHistory,
    };

    useEffect(() => {
        fetch("/users/locked/history").then(response => {
            if (response.ok) {
                return response.json();
            }

            throw response;
        }).then(data => {
            let lockedUserHistoryList = [];

            Object.values(data).forEach(events => {
                lockedUserHistoryList = events;
            });

            setLockedUSerHistory(lockedUserHistoryList);
        }).catch(error => {
            console.error(error);
        })
    }, []);

    return (
        <div style={ { display: 'flex', height: '100vh' } }>
            <Navbar/>
            <CDBCard id={"data-card"}>
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

export default LockedUserHistory;