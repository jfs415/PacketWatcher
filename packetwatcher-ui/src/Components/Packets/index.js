import React, { useEffect, useState } from 'react';
import { CDBCard, CDBCardBody, CDBDataTable } from "cdbreact";
import Navbar from "../Navbar";
import "./styles.css";

const Packets = () => {

    const [packets, setPackets] = useState([]);

    const data = {
        columns: [
            {
                label: "Timestamp",
                field: "timestamp",
                sort: "desc",
            },
            {
                label: "Destination Host",
                field: "destinationHost",
            },

            {
                label: "Destination Port",
                field: "destinationPort",
            },
            {
                label: "Flagged Country",
                field: "flaggedCountry",
            },
        ],

        rows: packets,
    };

    useEffect(() => {
        fetch("/packets").then(response => {
            if (response.ok) {
                return response.json();
            }

            throw response;
        }).then(data => {
            let packetData = [];

            Object.values(data).forEach(packets => {
                packetData = packets;
            });

            setPackets(packetData);
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
                                  searching={ false }
                                  entries={ 10 }
                                  pagesAmount={ 4 }
                                  data={ data }
                                  materialSearch={ true }
                                  noBottomColumns={ true }
                    />
                </CDBCardBody>
            </CDBCard>
        </div>
    );
};

export default Packets;