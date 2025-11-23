## All Services Details

### Services used 


### `<Eureka-server-ServiceRegistry>`
* [Oficial Eureka-server-ServiceRegistry URI](http://localhost:8761/)
  * http://localhost:8761/


### `<Patient-Service>`
* [Oficial Patient-Service URI](http://localhost:8080/api/v1/patients)
    * http://localhost:8080/api/v1/patients
  
### `<Doctor-Service>`
* [Oficial Doctor-Service URI](http://localhost:8081/api/v1/doctors)
  * http://localhost:8081/api/v1/doctors

### `<Appointment-Service>`
* [Oficial Appointment-Service URI](http://localhost:8083/api/v1/appointments)
  * http://localhost:8083/api/v1/appointments

### `<Api-GateWay>`
* [Oficial Api-Gateway URI](http://localhost:8084/api/v1/)
    * http://localhost:8084/

### `<Kafka-test-Serv>`
* [Oficial Kafka-server URI](http://localhost:8089)
    * http://localhost:8089/


* RelationShips And  redis rateLimit are used in Patient service
* Feign client used In Appointment Server for Fein were Patient and Doctors are Clients 
* Eureka server is used to Service Discovery and 
* Api Gateway also implimented 
* Kafak implimented between appointment and Patient
* Grpc is implimented between Patient and Appointments