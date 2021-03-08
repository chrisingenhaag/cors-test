# Spring-cloud-gateway CORS test-project

A sample basic test project for spring-cloud-gateway showing that global cors configuration seems to be broken.

Or something is configured wrong https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#cors-configuration

- spring-boot 2.4.3
- spring-cloud 2020.0.1


## Test manually

```
./gradlew bootRun
curl -i http://localhost:8080/api/users/2
```

See that response doesn´t contain the configured CORS headers

## Test automatically

Run the CorsConfigurationTest with gradle to test correct CORS Header in response. 

```
./gradlew test
```

