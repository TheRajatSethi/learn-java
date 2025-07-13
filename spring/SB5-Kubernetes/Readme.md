# Project

**Objective** : 
Create a movie based rest api application and deploy on kubernetes, explore kubernetes offerings. Using minikube as a local kubernetes distribution to run on the machine.

---

## Part-1 : Create container
 
- Create a basic rest api with 1 endpoint and no db connection
- Create a container with a basic dockerfile
- Deploy and run on docker

### Create Container

A basic Dockerfile copies the built `jar` into container and runs it via `ENTRYPOINT`. 

```Dockerfile
FROM sapmachine:20
VOLUME /tmp
COPY target/*.jar server.jar
ENTRYPOINT ["java","-jar","/server.jar"]
```

Created a basic dockerfile above, however if you look at the 
[Guide](https://spring.io/blog/2018/11/08/spring-boot-in-a-container) there is more optimization which can be done.

Build docker image `docker build . --tag api`. This will build image and tag with name `api`

### Running container

Run docker image `docker run -p 8080:8080 api `. This will run the `api` image in a new container with port forwarding. `8080 -> 8080`.


---

## Part-2 : Database

Objective : Connect to SQL DB, Get and Put operations for resources.

### Overview of the persistence jungle in Java

- Hikari is a connection pooling library.
- There are various levels of abstraction of persistence which can be added to the project.
  - Plain JDBC
    - Plain jdbc has no default integration with the spring framework where you need to manually handle everything such as creating connection, managing pools etc... 
  - Spring JDBC
    - Provides certain abstractions on plain JDBC and does the connection plumbing for you. You can use the `JdbcTemplate` for running queries and updates on database without managing resources such as connection etc...
    - One needs to be careful when using `JdbcTemplate` some of the methods use `statement` vs other using `preparedStatement`.
  - Spring Data with JDBC
    - Here you start to get the benefits of Spring Data along with JDBC such as CRUD repository interface, orm mapping (it is not as complex as JPA, also lacks some features compared to JPA).
    - Support for transparent auditing (created, last changed)
    - ...
  - Spring Data JPA
    - JPA does a lot of things such as 
      - caching (Level 1, Level 2 and query cache)
      - Navigation between entities
      - Lazy loading
    - Spring data JPA combines the benefits of using JPA with Spring Data
    - ...

Spring Data umbrella has many other projects under its belt which deal with persistance such as
  - LDAP
  - Mongo
  - Redis
  - R2DBC - reactive
  - Elastic
  - Hadoop
  - ...

### Connect to SQ DB

- Run postgres as a docker container
`docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres`
- A better way however will be to install postgres and then connect to it else mount external volume on the container as not to loose data.
- Connect to postgres via dbeaver

Below configuration supports connection to postgres.

```yaml
Spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/projects?currentSchema=movie
    username: postgres
    password: postgres
```



---

## Part-x : Deploy on kubernetes

Using minikube on Windows machine.

## Properties File - Env variables

Usually properties file can get data from 
- environment variables
- kubernetes config map
- ...

Get Website name from external sources
```yaml
website_name: ${website_name} # Comes from env variable or container or kubernetes config map
```


## Dockerfile - Env variables

Dockerfile can contain hard coded environment variables values e.g.

```Dockerfile
ENV website_name MovieDBApplication
```

Dockerfile can also get env variable values when the image gets built

```Dockerfile
ENV website_name $name
```

```shell
docker build -t movieapp --build-arg name=MovieDBApplication .
```

Docker container can also get env variable values when the container is run.

```shell
docker run --env website_name=MovieDBApplication
```










---

# TODO 
### Deploy on Kubernetes


### Connect the application with Database


### Create 2 pods for the application


### Multi-node deployment with minikube


### Kubernetes scheduler calls some endpoint on the application


---


## Monitoring and Performance Tuning


### Monitor logs - Kibana


### Performance monitoring
- How many threads are running
- How memory is being consumed
- Auto increase heap size and pod size if possible


### Heap Dumps - machine crashing

