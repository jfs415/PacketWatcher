# PacketWatcher

## Module Architecture
* <font size="3">Packetwatcher-Core
    * Core service that listens on a determined network interface to sniff and flag suspicious packets.
* <font size="3">Packetwatcher-API
    * Springboot RESTful API to interact with the core service.
* <font size="3">Packetwatcher-API
    * React front end to view raw packet capture data, analytics and interact with the system.

## System Requirements
These system requirements exist across each module in this application.
* Java 11
* MySQL 8.0 and above

## Tested Systems
* Windows 10.0.19045

## Dependencies
These dependencies exist across each module in this application.
* springboot-starter
* springboot-starter-test
* springboot-starter-data-jpa
* jakartaee-api-8.0.0
* findbugs-jsr305-3.0.2
* mysql-connector-8.0.<version>
* testng-7.1.0
