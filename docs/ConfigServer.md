## Getting Started

### Create a Config Server

#### 1. Dependencies
```groovy
implementation 'org.springframework.cloud:spring-cloud-config-server'
```

#### 4. Code
Declare `@EnableConfigServer`.

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

#### Define Configuration Properties

1. Define label that specifies branch to use from git repository
2. Specify uri to point config server at git repository

```yaml
spring:
  cloud:
    config:
      server:
        git:
          default-label: main
          uri: file:/${user.home}/config-repo
          skipSslValidation: true
```

#### Create Config Server Git Repository

```
mkdir config-repo
git init
echo info.foo: bar > application.properties
git add .
git commit -m"<message>"
```

#### Test it out

View [/application/default](http://localhost:8080/application/default)

```json
{
  "name": "application",
  "profiles": [
    "default"
  ],
  "label": null,
  "version": "9608b939c2a3694722fb37546d4e00e8e2df2fa2",
  "state": null,
  "propertySources": [
    {
      "name": "file:/<path>/config-repo/application.properties",
      "source": {
        "info.foo": "bar"
      }
    }
  ]
}
```

### Create a Config Client

#### 1. Dependencies
```groovy
	implementation 'org.springframework.boot:spring-boot-starter-actuator'
	implementation 'org.springframework.cloud:spring-cloud-starter-config'
```

#### Define Configuration Properties

```yaml
spring:
  config:
    import: configserver:http://localhost:8080

management:
  endpoints:
    enabled-by-default: true
    web:
      exposure:
        include: '*'
```

#### Test it out
View `/actuator/env`

```json
{
  "name": "configserver:file:/<path>/config-repo/application.properties",
  "properties": {
    "info.foo": {
      "value": "******",
      "origin": "Config Server file:/<path>/config-repo/application.properties:1:11"
    }
  }
}
```