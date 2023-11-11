import React, { useEffect, useState } from 'react';
import Navbar from "../Navbar";
import { CDBCard, CDBCardBody, CDBDataTable } from "cdbreact";
import {getFromLocalStorage} from "../../util/LocalStorage";

const Users = () => {

    const [users, setUsers] = useState([]);

    const data = {
        columns: [
            {
                label: "Username",
                field: "username",
                sort: "desc",
            },
            {
                label: "First Name",
                field: "firstName",
            },

            {
                label: "Last Name",
                field: "lastName",
            },
            {
                label: "Email",
                field: "email",
            },
            {
                label: "Phone",
                field: "phone",
            },
        ],

        rows: users,
    };

    useEffect(() => {
        fetch(`/users?token=${getFromLocalStorage("jwt")}`, {
            headers: {
                "Content-Type": "application/json"
            },
            method: "GET",
        }).then(response => {
            if (response.ok) {
                return response.json();
            }

            throw response;
        }).then(data => {
            let usersList = [];

            Object.values(data).forEach(users => {
                usersList = users;
            });

            setUsers(usersList);
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
                    />
                </CDBCardBody>
            </CDBCard>
        </div>
    );
};

export default Users;