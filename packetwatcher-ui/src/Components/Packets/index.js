import React, {useEffect, useState} from 'react';
import {CDBCard, CDBCardBody, CDBDataTable} from "cdbreact";
import Navbar from "../Navbar";

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
                label: "Source Host",
                field: "sourceHost",
            },

            {
                label: "Source Port",
                field: "sourcePort",
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
        <div style={{display: 'flex', height: '100vh'}}>
            <Navbar/>
            <CDBCard id={"data-card"}>
                <CDBCardBody style={{paddingTop: "5%"}}>
                    <CDBDataTable className={"data-table"}
                                  striped
                                  bordered
                                  hoveCDBDataTable
                                  searching={false}
                                  entries={10}
                                  pagesAmount={4}
                                  data={data}
                                  materialSearch={true}
                                  noBottomColumns={true}
                    />
                </CDBCardBody>
            </CDBCard>
        </div>
    );
};

export default Packets;