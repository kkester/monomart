## Getting Started
### 1. Docker
```yaml
  rabbitmq:
    image: rabbitmq:management
    container_name: 'rabbitmq'
    ports:
      - 5672:5672
      - 15672:15672
```
Use [RabbitMQ UI](http://localhost:15672) to view management console
username/pw: guest/guest

### 2. Dependencies
```groovy
    implementation platform('org.springframework.cloud:spring-cloud-dependencies:2022.0.3')
    implementation 'org.springframework.cloud:spring-cloud-starter-stream-rabbit'

    testImplementation 'org.springframework.amqp:spring-rabbit-test'
```

## Publish Events
### 3. Configuration Properties
```yaml
spring:
  cloud:
    stream:
      bindings:
        myEvent:
          destination: event-topic
```

### 4. Code
```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final StreamBridge streamBridge;
    public void publishEvent() {
        MyEvent myEvent = MyEvent.builder().build();
        streamBridge.send("myEvent", myEvent);
    }
}
```

## Consuming Events

```yaml
spring:
  cloud:
    stream:
      function:
        definition: consumeEvent
      bindings:
        myEvent-in-0:
          destination: event-topic
```

```java
@Configuration
public class ConsumerConfig {
    @Bean
    public Consumer<MyEvent> consumeEvent(MyService service) {
        return service::processEvent;
    }
}
```