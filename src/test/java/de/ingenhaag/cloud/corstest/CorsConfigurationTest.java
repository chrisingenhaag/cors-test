package de.ingenhaag.cloud.corstest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "customservice.service.uri=http://localhost:${mockServerPort}",
    })
@MockServerTest
public class CorsConfigurationTest {

  @Value("http://localhost:${local.server.port}")
  private String baseUrl;

  protected WebTestClient webTestClient;

  protected MockServerClient mockServerClient;

  @BeforeEach
  public void configureWebTestClient() {
    webTestClient = WebTestClient.bindToServer()
        .baseUrl(baseUrl).build();
  }

  @Test
  public void corsHeadersShouldBeApplied() {
    mockServerClient.when(request().withMethod("GET")
        .withPath("/api/test"))
        .respond(response()
            .withBody("{ \"somebody\": \"true\" }")
            .withContentType(MediaType.APPLICATION_JSON)
            .withStatusCode(200));

    webTestClient.get()
        .uri("/api/test")
        .exchange()
        .expectStatus().isOk()
        .expectHeader().exists(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
        .expectHeader().exists(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS)
        .expectHeader().exists(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS)
        .expectHeader().exists(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS);
  }

}
