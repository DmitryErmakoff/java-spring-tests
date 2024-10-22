package ru.d3m4k.javaspringtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class OpenApiTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest")
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @Test
    public void shouldReturnHelloMessage_WhenSendingGetRequest() {
        String expectedResult = "Hello, someone";

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();

        String receivedResult = response.jsonPath().getString("answer");
        assertThat(receivedResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldAddPetSuccessfully_WhenProvidingValidPetData() {
        String jsonBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"doggie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .andReturn();

        response.prettyPrint();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
