import "./styles.css";
import React, {useEffect, useState} from "react";
import {json} from "d3-fetch";
import {scaleLinear} from "d3-scale";
import {
    ComposableMap,
    Geographies,
    Geography,
    Sphere,
    Graticule, ZoomableGroup
} from "react-simple-maps";
import Navbar from "../Navbar";

const geoUrl = "/features.json";

const colorScale = scaleLinear()
    .domain([0.29, 0.68])
    .range(["#ffedea", "#ff5233"]);

const loadGeographies = () => {
    return new Promise((resolve) => {
        json(`/features.json`).then((data) => {
            let countryData = [];

            data['objects']['world']['geometries'].forEach(d => {
                countryData.push({
                    'ISO3166': d['id'],
                    'name': d['properties']['name'],
                    'count': 0
                });
            });
            resolve(countryData);
        }); 
    });
}

const loadData = (countryData) => {
    return new Promise((resolve) => {
        fetch(`/dashboard`).then(response => {
            if (response.ok) {
                return response.json();
            }
            throw response;
        }).then(data => {
            let updatedCountryData = countryData;

            data['views'].forEach(view => {
                updatedCountryData.forEach(country => {
                    if (view['country'] === country['ISO3166']) {
                        country['count'] = view['count'];
                    }
                });
            });

            resolve(updatedCountryData);
        }).catch(error => {
            console.error(error);
        });
    });
}

const Dashboard = ({ setTooltipContent }) => {
    const [countryData, setCountryData] = useState([]);
    
    useEffect(() => {
        loadGeographies().then((geo) => loadData(geo)).then(setCountryData);
    }, []);

    return (
        <div style={{display: 'flex', height: '100vh', overflow: 'scroll initial', background: '#242526'}}>
            <Navbar/>
            <ComposableMap className={"dashboard-map"}
                           projectionConfig={{
                               rotate: [-10, 0, 0],
                               scale: 147
                           }}>
                <ZoomableGroup>
                    <Sphere stroke="#E4E5E6" strokeWidth={0.0}/>
                    <Graticule stroke="#E4E5E6" strokeWidth={0.0}/>
                    {countryData.length > 0 && (
                        <Geographies geography={geoUrl}>
                            {({geographies}) =>
                                geographies.map((geo) => {
                                    const id = countryData.find((s) => s.ISO3166 === geo.id);
                                    return (
                                        <Geography
                                            key={geo.rsmKey}
                                            geography={geo}
                                            fill={id ? colorScale(id["count"]) : "#F5F4F6"}
                                            onMouseEnter={() => {
                                                setTooltipContent(`${geo.properties.name}`);
                                            }}
                                            onMouseLeave={() => {
                                                setTooltipContent("");
                                            }}
                                        />
                                    );
                                })
                            }
                        </Geographies>
                    )}
                </ZoomableGroup>
            </ComposableMap>
        </div>
    );
}

export default Dashboard;