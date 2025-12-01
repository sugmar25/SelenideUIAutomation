package org.gallup.access.stepdefinations;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.gallup.access.pojo.Users;
import org.gallup.access.utils.ApiRequestSpec;
import org.gallup.access.utils.JwtUtils;
import org.gallup.access.utils.OAuth2TokenManager;
import org.gallup.access.utils.PayloadReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;

public class OrderApiSteps {

    private Response response;
    private String token;

    @When("I fetch order details for ID {string}")
    public void iFetchOrderDetails(String orderId) {
        token = OAuth2TokenManager.getAccessToken();
        response = given().spec(ApiRequestSpec.getSpec(token)).get("/orders/" + orderId);

    }

    @Then("the order status should be {string}")
    public void theOrderStatusShouldBe(String expectedStatus) {
        String actualStatus = response.jsonPath().getString("status");
        assertEquals(expectedStatus, actualStatus);

    }

    @Given("Get the value from the API")
    public void getTheValueFromTheAPI() {
        try {
            this.response = given().spec(ApiRequestSpec.getEcho()).get("/get");
            String actualStatus = response.jsonPath().toString();
            int statusCode = response.getStatusCode();
            assertEquals(statusCode, 200);
            System.out.println(actualStatus);
            String jsonString = response.getBody().asString();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonString);
            // Navigate to headers.host
            String actualHost = root.path("headers").path("host").asText();
            System.out.println("Host value: " + actualHost);

            // Assert the expected value
            assert actualHost.equals("postman-echo.com") : "Host mismatch: " + actualHost;


            // Pretty print the entire JSON
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
            System.out.println("Full JSON Response:\n" + prettyJson);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Then("Post the API and validate them")
    public void postTheAPIAndValidateThem() {
        try {
            String payload = PayloadReader.getPayload("src/test/java/org/gallup/access/payload/PostPayload.json");
            this.response = given().spec(ApiRequestSpec.getEcho()).body(payload).post("/post");
            System.out.println("Response:\n" + response.getBody().asPrettyString());
            assertEquals(response.getStatusCode(), 200);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("Validate the post response")
    public void validateThePostResponse() {
        String jsonString = response.getBody().asString();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonString);

            // Navigate to the "json" array
            JsonNode jsonArray = root.path("json");

            boolean found = false;
            for (JsonNode book : jsonArray) {
                String title = book.path("title").asText();
                if ("Moneyball".equals(title)) {
                    found = true;
                    System.out.println("Found title: " + title);
                    break;
                }
            }

            Assert.assertTrue(found, "Title 'Moneyball' not found in json array");

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse or validate JSON response", e);
        }

    }

    @Test
    public void testProtectedEndpoint() {
        Response response = RestAssured
                .given()
                .spec(OAuth2TokenManager.getRequestSpec())
                .get("/protected/resource");

        response.then().statusCode(200);
    }

    public static Map<String, Object> retrieveJWTtoken() {
        Map<String, Object> claims = JwtUtils.getClaims(OAuth2TokenManager.getAccessToken());
        System.out.println("Expiry: " + claims.get("exp"));
        System.out.println("Subject: " + claims.get("sub"));
        return claims;
    }

    @Test
    public void testJsonSchema() {
        given()
                .get("/users/1")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("user-schema.json"));
    }

    public static String jsonresponsereader() throws JsonProcessingException {
        Users user = new Users();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }

    public static Users jsonresponewriter() throws JsonProcessingException {
        Users user = new Users();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        return mapper.readValue(jsonString, Users.class);
    }
    //Serialization Example
    @Test
    public void createUser() {
        Users user = new Users(1, "Sugumar", "Sugumar@example.com", "IT", "Tester");
        given()
                .contentType(ContentType.JSON)
                .body(user) // POJO automatically serialized to JSON
                .when()
                .post("/users")
                .then()
                .statusCode(201);
    }

    //Deserialization Example
    @Test
    public void getUser() {
        Users user = given()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Users.class); // JSON → POJO

        // Assertions on POJO fields
        assertEquals(1, user.getId());
        assertEquals("Brinda", user.getName());
    }




}
