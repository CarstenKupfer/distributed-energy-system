# Distributed Energy System

## Overview

This project implements an energy community simulation using multiple distributed services.

Energy production and energy consumption messages are exchanged through RabbitMQ. The usage service calculates the hourly energy distribution and stores the results in PostgreSQL. A current percentage service calculates the current energy statistics. A REST API provides access to the data and a JavaFX GUI displays the current status and historical values.

## Components

* disys-energy-producer
* disys-energy-user
* disys-usage-service
* disys-current-percentage-service
* disys-energy-api
* disys-energy-gui

## Technologies

* Java 21
* Spring Boot
* RabbitMQ
* PostgreSQL
* JavaFX
* REST API
* Open-Meteo Weather API

## Features

### Energy Producer

* Sends producer messages every few seconds
* Uses weather information from Open-Meteo
* Produces more energy when cloud cover is low

### Energy User

* Sends user messages every few seconds
* Uses time-of-day dependent consumption
* Higher consumption during morning and evening peak hours

### Usage Service

* Consumes producer and user messages
* Aggregates minute values into hourly values
* Calculates community usage and grid usage

### Current Percentage Service

* Calculates:

  * Community pool depletion percentage
  * Grid portion percentage

### REST API

Available endpoints:

GET /energy/current

Returns the current percentage values.

GET /energy/historical?start=...&end=...

Returns historical usage data for the selected period.

### GUI

* Displays current percentage values
* Displays historical data
* Uses the REST API (no direct database access)

## Prerequisites

- Docker Desktop
- Java 21

## Startup Order

1. Start Docker Desktop
   (PostgreSQL and RabbitMQ containers)
   
2. disys-energy-producer
3. disys-energy-user
4. disys-usage-service
5. disys-current-percentage-service
6. disys-energy-api
7. disys-energy-gui

## Testing

A unit test verifies the usage calculation logic based on the example provided in the project specification.

## Team

#### Developed by: 
Annemarie Strele, Michael Reithofer, David Stevic  
