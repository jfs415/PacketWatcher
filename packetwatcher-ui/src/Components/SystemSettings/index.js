import React, { useEffect, useState } from 'react';
import Navbar from "../Navbar";
import { CDBCard, CDBCardBody, CDBDataTable } from "cdbreact";

const SystemSettings = () => {

    const [settings, setSettings] = useState([]);

    const data = {
        columns: [
            {
                label: "Setting Key",
                field: "settingKey",
                sort: "desc",
            },
            {
                label: "Setting Value",
                field: "settingValue",
            },
        ],

        rows: settings,
    };

    useEffect(() => {
        fetch("/system/settings").then(response => {
            if (response.ok) {
                return response.json();
            }

            throw response;
        }).then(data => {
            let settingsList = [];

            Object.values(data).forEach(settings => {
                settingsList = settings;
            });

            setSettings(settingsList);
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
                                  searching={ false }
                                  sortable={ false }
                    />
                </CDBCardBody>
            </CDBCard>
        </div>
    );
};

export default SystemSettings;