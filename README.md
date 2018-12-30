# IssueLogServer

A demonstration application using Spring Boot, Kotlin, Elasticsearch
and Maven.

The application provides a back-end capability for storing and querying
issue logs, using Elasticsearch indexing.

## Build and run
Install and start Elasticsearch (required for tests to pass). The
server and port are read from the `application.yaml` file.

The Maven build generates the target package `issuelog-server-{version}-spring.jar`
which is a Spring Boot executable jar file.

The included Postman collection provides example requests and payloads.
 
## TODO
- Enable OAuth2 authentication and authorization: Currently the application has
no authentication required.