import React from 'react';
import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarFooter,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem
} from "cdbreact";
import { NavLink } from "react-bootstrap";

const Navbar = () => {
    return (
        <CDBSidebar textColor="#fff" backgroundColor="#333">
            <CDBSidebarHeader prefix={ <i className="fa fa-bars fa-large"></i> }>
                <a href="/dashboard" className="text-decoration-none" style={ { color: 'inherit' } }>
                    PacketWatcher
                </a>
            </CDBSidebarHeader>

            <CDBSidebarContent className="sidebar-content">
                <CDBSidebarMenu>
                    <NavLink href="/packets" activeClassName="activeClicked">
                        <CDBSidebarMenuItem icon="envelope">Captured Packets</CDBSidebarMenuItem>
                    </NavLink>
                    <NavLink href="/system/settings" activeClassName="activeClicked">
                        <CDBSidebarMenuItem icon="cog">System Settings</CDBSidebarMenuItem>
                    </NavLink>
                    <NavLink href="/profile" activeClassName="activeClicked">
                        <CDBSidebarMenuItem icon="user">User Profile</CDBSidebarMenuItem>
                    </NavLink>
                    <NavLink href="/users" activeClassName="activeClicked">
                        <CDBSidebarMenuItem icon="users">Users</CDBSidebarMenuItem>
                    </NavLink>
                    <NavLink href="/users/locked/history" activeClassName="activeClicked">
                        <CDBSidebarMenuItem icon="users">Locked User History</CDBSidebarMenuItem>
                    </NavLink>
                    <NavLink href="/system/analytics" activeClassName="activeClicked">
                        <CDBSidebarMenuItem icon="chart-line">System Analytics</CDBSidebarMenuItem>
                    </NavLink>
                    <NavLink href="/events/authentication" activeClassName="activeClicked">
                        <CDBSidebarMenuItem icon="star">Authentication Events</CDBSidebarMenuItem>
                    </NavLink>
                    <NavLink href="/events/authorization" activeClassName="activeClicked">
                        <CDBSidebarMenuItem icon="star">Authorization Events</CDBSidebarMenuItem>
                    </NavLink>
                </CDBSidebarMenu>
            </CDBSidebarContent>

            <CDBSidebarFooter style={ { textAlign: 'center' } }>
                <div style={ { padding: '2em 5em' } }>
                    <a className="password" href="/logout">Logout</a>
                </div>
            </CDBSidebarFooter>
        </CDBSidebar>
    );
};

export default Navbar;