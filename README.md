# Vehicle Tracking Management Project
This repository contains the source code for the basis of The Vehicle Tracking Management Project (VTMProject).

# Contributions
This project has been prepared with the aim of using some technologies and methods to develop spring boot capabilities.
# Technology Stack

| Tech                    | Url                                                                                       |
|-------------------------|-------------------------------------------------------------------------------------------|
| Spring Boot Data Jpa    | implementation 'org.springframework.boot:spring-boot-starter-data-jpa'                    |
| Spring Boot Actuator    | implementation 'org.springframework.boot:spring-boot-starter-actuator'                    |
| Spring Boot Security    | implementation 'org.springframework.boot:spring-boot-starter-security'                    |
| Spring Boot Web         | implementation 'org.springframework.boot:spring-boot-starter-web'                         |
| Lombok                  | compileOnly 'org.projectlombok:lombok'-----annotationProcessor 'org.projectlombok:lombok' |
| PostgreSQL              | runtimeOnly 'org.postgresql:postgresql'                                                   |
| Swagger Ui              | implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'                  |
|
| Spring Boot validation' | implementation 'org.springframework.boot:spring-boot-starter-validation'                  |

# How to Run ?
✔ Clone Repository

✔ Before using the application, you must configure the database and mail configuration in the application.yml file.

✔ Run the VTMProjectApplication.java

✔ Run [Swagger UI](http://localhost:9090/swagger-ui/index.html#/)

✔ You can send requests to endpoints

# Conditions in the project

→ If the customer is not registered in the system, customer must first register

→ To register the system we must create a company → [CreateCompany](http://localhost:9090/swagger-ui/index.html#/company-controller/createCompany#/)

→ After Create the Company We can Create UserProfile → [REGISTER](http://localhost:9090/swagger-ui/index.html#/auth-controller/register#/)

→ We can only choose ADMIN, MANAGER and STANDARD roles.

→ We can login now. [LOGIN](http://localhost:9090/swagger-ui/index.html#/auth-controller/authenticate#/)

→ After login - if we succeed- EndPoint return us a Token.

→ We must Authenticate via Swagger Authenticate. As I show in the image below

![Authenticate](https://res.cloudinary.com/doqksh0xh/image/upload/v1691926966/LoginImage_coeh8k.png)

→ In some methods, tokens may be requested for control. Please be careful.

→ Now, after all we've done, We can create vehicle. (If our roles ADMIN or MANAGER) [CREATE VEHICLE](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/createVehicle#/)

→ We need RegionId, FleetId, GroupId and VehicleId to run this endpoint.
→ To crate them We can use these endpoints.s

→ These features are interconnected. A group cannot be created without a fleet and a fleet cannot be created without a region.  

→ [CREATE REGION](http://localhost:9090/swagger-ui/index.html#/region-controller/createRegion#/)
→ [CREATE FLEET](http://localhost:9090/swagger-ui/index.html#/fleet-controller/createFleet#/)
→ [CREATE GROUP](http://localhost:9090/swagger-ui/index.html#/group-controller/createGroupn#/)

→ Vehicle has Region, Fleet and Group to assign these features to vehicle, We will use this endpoint [ZONE UPDATE](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/zoneUpdate#/)

### TREE CHART
→ One of the best features of this app is a service that returns user-authorized vehicles in a tree structure based on their region.

→ [TREE CHART](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/vehicleTreeByUserId#/)

→ Outputs
![TreeJson](https://res.cloudinary.com/doqksh0xh/image/upload/v1691928599/treeJson_lpsezo.png)

![TreeConsole](https://res.cloudinary.com/doqksh0xh/image/upload/v1691928636/treeConsole_e29pnm.png)

# EndPoints
→ ALL PUT AND POST methods can only use the ADMIN and MANAGER roles.

→ STANDARD roles can use just GET methods in this application.
### Vehicle
#### PUTS
→ [ZONE UPDATE](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/zoneUpdate#/)

→ [UPDATE VEHICLE](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/updateByVehicleId#/)

→ [Auth Vehicle By Manager](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/authVehicleByManager#/)

→ [Assign Manager To Region](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/zoneUpdate#/)
#### POST
→ [Create Vehicle](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/createVehicle#/)

#### GETS
→ [TREE CHART](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/assignManagerToRegion#/)

→ [Get By Vehicle Id](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/getByVehicleId#/)

→ [Get All Vehicle By CompanyId And UserId](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/getAllVehicleByCompanyIdAndUserId#/)

→ [Get All Vehicle By CompanyId](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/getAllVehiclebyCompanyId#/)
#### DELETE
→ [DELETE](http://localhost:9090/swagger-ui/index.html#/vehicle-controller/deleteByVehicleId#/)

### UserProfile
#### PUT
→ [UPDATE](http://localhost:9090/swagger-ui/index.html#/user-profile-controller/updateByUserId#/)
#### POST
→ [GET USER BY USERID](http://localhost:9090/swagger-ui/index.html#/user-profile-controller/getByUserId#/)
#### DELETE
→ [DELETE](http://localhost:9090/swagger-ui/index.html#/user-profile-controller/deleteByUserId#/)

### Region
#### PUT
→ [UPDATE](http://localhost:9090/swagger-ui/index.html#/region-controller/updateByRegionId#/)
#### POST
→ [CREATE REGION](http://localhost:9090/swagger-ui/index.html#/region-controller/createRegion#/)

→ [GET REGION](http://localhost:9090/swagger-ui/index.html#/region-controller/getByRegionId#/)
#### DELETE
→ [DELETE REGION](http://localhost:9090/swagger-ui/index.html#/region-controller/deleteByRegionId#/)


### Fleet
#### PUT
→ [UPDATE](http://localhost:9090/swagger-ui/index.html#/fleet-controller/updateByFleetId#/)
#### POST
→ [CREATE FLEET](http://localhost:9090/swagger-ui/index.html#/fleet-controller/createFleet#/)

→ [GET FLEET](http://localhost:9090/swagger-ui/index.html#/fleet-controller/getByFleetId#/)
#### DELETE
→ [DELETE FLEET](http://localhost:9090/swagger-ui/index.html#/fleet-controller/deleteByFleetId#/)


### Group
#### PUT
→ [UPDATE](http://localhost:9090/swagger-ui/index.html#/group-controller/updateByGroupId#/)

#### POST
→ [CREATE GROUP](http://localhost:9090/swagger-ui/index.html#/group-controller/createGroupn#/)

→ [GET GROUP](http://localhost:9090/swagger-ui/index.html#/group-controller/getByGroupId#/)
#### DELETE
→ [DELETE GROUP](http://localhost:9090/swagger-ui/index.html#/group-controller/deleteByGroupId#/)


### Company
#### PUT
→ [UPDATE](http://localhost:9090/swagger-ui/index.html#/group-controller/updateByGroupId#/)
#### POST
→ [CREATE COMPANY](http://localhost:9090/swagger-ui/index.html#/company-controller/createCompany#/)

→ [GET COMPANY](http://localhost:9090/swagger-ui/index.html#/company-controller/getByCompanyId#/)
#### DELETE
→ [DELETE COMPANY](http://localhost:9090/swagger-ui/index.html#/company-controller/deleteByCompanyId#/)



### Auth
[REGISTER](http://localhost:9090/swagger-ui/index.html#/auth-controller/register#/)

[LOGIN](http://localhost:9090/swagger-ui/index.html#/auth-controller/authenticate#/)

## What Did I Try To Do
As I said before, this was a project that laid the foundations of an automobile tracking project and would give an idea to those who wanted it.
Technologies used in the project (such as Security), architectural structure, validation examples, Data Transfer Objects and Views can serve as a cornerstone for those who want to do this project or a similar one.
