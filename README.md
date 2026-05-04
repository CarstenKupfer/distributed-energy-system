# Distributed Energy System

This project is part of the *Distributed Systems* course.

It simulates an energy community where energy is produced, consumed, and distributed between a local community and the power grid.

---

## Project Structure
distributed-energy-system/
  disys-energy-api/ # Spring Boot REST API
  disys-energy-gui/ # JavaFX GUI
  
---

## Technologies

- Java 21
- Spring Boot (REST API)
- JavaFX (GUI)
- Maven

---

## REST API

The REST API provides energy data using example (simulated) values.

### Endpoints

#### GET /energy/current
Returns the current energy distribution:

```json
{
  "communityDepleted": 100.0,
  "gridPortion": 5.63
}
```
#### GET /energy/historical?start=...&end=...

Returns historical hourly energy usage:

Example:
``` 
/energy/historical?start=2025-01-10T12:00:00&end=2025-01-10T15:00:00
```
Response:
```
[
  {
    "hour": "2025-01-10T14:00:00",
    "communityProduced": 18.05,
    "communityUsed": 18.05,
    "gridUsed": 1.076
  }
]
```

## GUI (JavaFX)

The GUI allows users to:

- View current energy distribution  
- Select a time range  
- Display historical energy data in a table  

The GUI communicates with the REST API via HTTP.

---

## How to Run

### 1. Start REST API

```bash
cd disys-energy-api
.\mvnw.cmd spring-boot:run
```

---

### 2. Start GUI

```bash
cd disys-energy-gui
.\mvnw.cmd javafx:run
```

---

## Notes

- The system uses **simulated data** (no database required for this milestone)  
- The GUI is **not directly connected to a database**  
- REST API must be running before starting the GUI  

---

## Team

#### Developed by: 
Annemarie Strele, Michael Reithofer, David Stevic  
