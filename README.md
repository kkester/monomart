# Monomart

Monomart is a sample application written with Spring Boot that is intended to serve as a medium in which to teach and exercise the decomposition of a monolith into separate services.   

## Components

The makeup of Monomart is a Spring Boot web application, with a react frontend being served out from the embedded Tomcat.  This means that when the application is run, the UI is accessible from your browser on https://localhost:8080 (by default).  Running `yarn start` from the frontend folder which will make the app accessible at http://localhost:3000 is also supported.

## To run the application
First, launch `RabbitMQ` in a docker container by running `docker run -d --name rabbitmq -p15672:15672 -p5672:5672 rabbitmq:management`.

Ensure that you are running the application with Java 17.
* Run the frontend application `./gradlew bootRun`
* Run the commerce application `./gradlew commerce:bootRun`
* Run the inventory application `./gradlew inventory:bootRun`
* Run the API Gateway application `./gradlew agw:bootRun`

## Dev Environment
Execute `./buildScript.sh` to compile, build, and publish a docker image for each microservice.
Run `docker compose up -d` to launch all applications and services in docker containers.

## Workshop Scenario

You have been called into Monomart inc to help their software team modernize and re-platform the application onto a container-based platform, Kubernetes for example.  Although the application is currently limited in scope, they anticipate a heavy influx of users in the coming months, potentially even reaching millions of purchases a second.   Throughout the modernization effort the application will need to remain online.  Additionally, depending on the duration of the refactor, additional features may need to be added to the Monomart application.

## Workshop Tasks

### Planning 
- [x] Analyze the application
- [ ] Determine decomposed architecture 
  - [ ] Weigh pros/cons of the architecture
  - [ ] Iterate
- [ ] Determine decomposition approach
 
### Execution
- [ ] Refactor
- [ ] Regression Test
- [ ] Repeat

## Things to think about

### Testing

The current testing on Monomart is lackluster at best.  Some work, some don't.  Many haven't been updated in many commits.  A special emphasis should be put on tests of various types to ensure that this refactor, and future refactors and feature updates do not lead to regressions. For the sake of the workshop, create a few tests that cover the span of the 

### Refactoring approach

We cant throw away our agile and XP tenants when doing a modernization, if anything, its more important that we uphold them during a modernization.  This means that agile, TDD, pairing, frequent releases, should all be core to what we do during a modernization.  This means that we should steer clear of a "big bang" refactor, making all the updates in a vacuum and releasing the software at some far future date.  Rather, we should devise a plan that allows us to iteratively refactor,  ensuring to deploy our application frequently to staging then production, during the refactor.

### CI/CD

When doing this for real, getting a pipeline running for the application(s) is just as important as tests in order to shorten the feedback and deployment loop.  For the purposes of this workshop, we will not build out about the CI/CD pipeline, but we should be thinking about it as we progress.