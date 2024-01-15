import React, {useEffect, useState} from 'react';
import Navbar from "../Navbar";
import {CDBCard, CDBCardBody} from "cdbreact";
import {
    Area,
    AreaChart,
    CartesianGrid,
    Pie,
    PieChart,
    Tooltip,
    XAxis,
    YAxis
} from "recharts";
import "./styles.css"

function Stat() {
    this.title = '';
    this.data = [];
}

const Stats = () => {
    const [countryStats, setCountryStats] = useState(new Stat());
    const [hostnameStats, setHostnameStats] = useState(new Stat());
    const [timeOfDayStats, setTimeOfDayStats] = useState(new Stat());
    const [dayOfWeekStats, setDayOfWeekStats] = useState(new Stat());
    const [timelineStats, setTimelineStats] = useState(new Stat());

    useEffect(() => {
        fetchData(5, "COUNTRY", "NONE");
        fetchData(5, "HOSTNAME", "NONE");
        fetchData(5, "TIME_OF_DAY", "NONE");
        fetchData(5, "DAY_OF_WEEK", "NONE");
        fetchTimelineStats(180);
    }, []);

    function fetchData(count: number, topic: string, orderBy: string) {
        fetch(`/stats/dashboard?count=${count}&topic=${topic}&orderBy=${orderBy}`).then(response => {
            if (response.ok) {
                return response.json();
            }
            throw response;
        }).then(data => {
            Object.values(data).forEach(stat => {
                Object.values(stat).forEach(statView => {
                    let fetchedStats = new Stat();

                    fetchedStats.title = statView['name'];
                    statView['data'].forEach(statData => {
                        fetchedStats.data.push({
                            name: String(statData['name']),
                            value: statData['recordCount']
                        });
                    });

                    // eslint-disable-next-line default-case
                    switch (topic) {
                        case "COUNTRY":
                            setCountryStats(fetchedStats);
                            break;
                        case "HOSTNAME":
                            setHostnameStats(fetchedStats);
                            break;
                        case "TIME_OF_DAY":
                            setTimeOfDayStats(fetchedStats);
                            break;
                        case "DAY_OF_WEEK":
                            setDayOfWeekStats(fetchedStats);
                            break;
                    }
                });
            });
        }).catch(error => {
            console.error(error);
        });
    }

    function fetchTimelineStats(days: number) {
        const today = new Date();

        let start = new Date();
        start.setDate(start.getDate() - days);

        fetch(`/stats/dashboard/timeline?startMillis=${start.getTime()}&endMillis=${today.getTime()}&days=${days}`).then(response => {
            if (response.ok) {
                return response.json();
            }
            throw response;
        }).then(data => {
            let fetchedStats = new Stat();

            fetchedStats.title = "Flagged Packets Caught Over the last " + days + " days";
            Object.values(data).forEach(stat => {
                Object.values(stat).forEach(data => {
                    fetchedStats.data.push({
                        name: String(data['date']),
                        amt: data['countOnDate']
                    });
                });
            });
            setTimelineStats(fetchedStats);
        }).catch(error => {
            console.error(error);
        });
    }

    return (
        <div style={{display: 'flex', height: '100vh'}}>
            <Navbar/>
            <CDBCard id={"data-card"}>
                <CDBCardBody style={{paddingTop: "5%", display: "grid"}}>
                    <div className={"stat-container"}>
                        <div className={"row"}>
                            <div className={"column"}>
                                <h4 className={"stat-header"}>{countryStats.title}</h4>
                                <PieChart width={250} height={250}>
                                    <Pie data={countryStats.data} dataKey='value' cx="50%" cy="50%"
                                         outerRadius={60} fill="#82ca9d" label/>
                                </PieChart>
                            </div>
                            <div className={"column"}>
                                <h4 className={"stat-header"}>{hostnameStats.title}</h4>
                                <PieChart width={250} height={250}>
                                    <Pie data={hostnameStats.data} dataKey='value' cx="50%" cy="50%"
                                         outerRadius={60} fill="#82ca9d" label/>
                                </PieChart>
                            </div>
                            <div className={"column"}>
                                <h4 className={"stat-header"}>{timeOfDayStats.title}</h4>
                                <PieChart width={250} height={250}>
                                    <Pie data={timeOfDayStats.data} dataKey='value' cx="50%" cy="50%"
                                         outerRadius={60} fill="#82ca9d" label/>
                                </PieChart>
                            </div>
                            <div className={"column"}>
                                <h4 className={"stat-header"}>{dayOfWeekStats.title}</h4>
                                <PieChart width={250} height={250}>
                                    <Pie data={dayOfWeekStats.data} dataKey='value' cx="50%" cy="50%"
                                         outerRadius={60} fill="#82ca9d" label/>
                                </PieChart>
                            </div>
                        </div>
                        <div className={"row"}>
                            <div className={"timeline-column"}>
                                <div className={"stat-timeline"}>
                                    <h2 className={"stat-header"}>{timelineStats.title}</h2>
                                    <div className={"timeline-chart"}>
                                        <AreaChart
                                            width={3000}
                                            height={400}
                                            data={timelineStats.data}
                                            margin={{
                                                top: 10,
                                                right: 30,
                                                left: 0,
                                                bottom: 0,
                                            }}>
                                            <CartesianGrid strokeDasharray="3 3"/>
                                            <XAxis dataKey="name"/>
                                            <YAxis/>
                                            <Tooltip/>
                                            <Area type="monotone" dataKey="amt" stroke="#8884d8" fill="#8884d8"/>
                                        </AreaChart>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </CDBCardBody>
            </CDBCard>
        </div>
    );
};

export default Stats;