package ru.d3m4k.javaspringtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaSpringTestsApplicationTests {

	@Test
	public void shouldReturnHelloMessage() {
		String expectedResult = "Hello, someone";

		Response response = RestAssured
				.get("https://playground.learnqa.ru/api/hello")
				.andReturn();

		String receivedResult = response.jsonPath().getString("answer");
		assertThat(receivedResult).isEqualTo(expectedResult);
	}

	@Test
	public void shouldAddPetSuccessfully() {
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
