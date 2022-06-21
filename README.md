## drone-delivery-api
Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

### Build
This project us implemented using Java Springboot framework. To build this project you can either install
maven and run the following command in the project directory
 - `mvn package`
 
Alternatively you can open the project using **Eclipse IDE** or **NetBean** build and run the project

---
### Assumptions and Approach
- This project uses in-memory database, i.e. the data is lost when the JVM restart
- When the server starts, a list(set) of medicines are created 
- A drone can carry load from the list of available medicines with possibility of duplication
- When a drone is initially registered, it status is *IDLE*
- A drone cannot be loaded with more than it's capacity
- Drone cannot be loaded when its battery is less than *25%*
- Only `IDLE` drone can be marked for loading
---

### Simulation
- Schedular run every `x`seconds
- Transition from one state to another takes *x* seconds i.e. delivery takes approx. *5x* seconds
- Drain drones battery every period as follows:
	- 1% for `IDLE` drones
	- 5% otherwise
	
### Endpoints
	- POST /api/drones/register  #Create new drone
	- GET /api/drones/available  # `IDLE` drones
	- GET /api/drones/{drone_id}/battery  #check `drone_id` current battery level
  - GET /api/drones/{drone_id}/load # return a list of drone loads
	- PATCH /api/drones/{drone_id}/load  #Add medicines to drone with id `drone_id`
