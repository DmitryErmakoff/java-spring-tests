package ru.d3m4k.javaspringtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenApiTest {

    @Test
    public void shouldReturnHelloMessageWhenSendingGetRequest() {
        String expectedResult = "Hello, someone";

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();

        String receivedResult = response.jsonPath().getString("answer");
        assertThat(receivedResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldAddPetSuccessfullyWhenProvidingValidPetData() {
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
