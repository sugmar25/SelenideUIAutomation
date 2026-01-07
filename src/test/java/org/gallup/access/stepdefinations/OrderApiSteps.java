package org.gallup.access.stepdefinations;


import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.internal.shadowed.jackson.core.JsonParser;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.gallup.access.payload.JobPayloadBuilder;
import org.gallup.access.utils.ApiRequestSpec;
import org.gallup.access.utils.JwtUtils;
import org.gallup.access.utils.OAuth2TokenManager;
import org.gallup.access.utils.PayloadReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.gallup.access.utils.JwtUtils.getClaims;
import static org.testng.AssertJUnit.assertEquals;

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
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Given("Get the value from the API")
    public void getTheValueFromTheAPI() {
        try {
            this.response = given().spec(ApiRequestSpec.getEcho()).get("/get");
            String actualStatus = response.jsonPath().toString();
            int statusCode = response.getStatusCode();
            Assert.assertEquals(statusCode, 200);
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
            Assert.assertEquals(response.getStatusCode(), 200);

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
    public void testProtectedEndpoint() throws IOException {
        Response response = RestAssured
                .given()
                .spec(OAuth2TokenManager.getRequestSpec())
                .get("/protected/resource");

        response.then().statusCode(200);

        //Deserizalization
        JobPayloadBuilder jobpayloadbuilder = RestAssured
                .given()
                .spec(OAuth2TokenManager.getRequestSpec())
                .get("/protected/resource")
                .then()
                .statusCode(200)
                .extract()
                .as(JobPayloadBuilder.class);

        assertEquals("Berlin",jobpayloadbuilder.getTitle());

        //way 2
        ObjectMapper mapper = new ObjectMapper();
        mapper.readValues((JsonParser) response,JobPayloadBuilder.class);

        //way3
        JobPayloadBuilder jobpayloadbuilder1= mapper.readValue(response.toString(),JobPayloadBuilder.class);
        jobpayloadbuilder1.getTitle();

        //Seriyalization with Builder design pattern
        JobPayload payload = new JobPayloadBuilderNew()
                .setTitle("QA Engineer")
                .setLocation("Berlin")
                .setSalary(80000)
                .build();

        JobPayload payload2 = new JobPayloadBuilderNew()
                .setTitle("QA Engineer")
                .setLocation("Berlin")
                .build();

        JobPayload payload3 = new JobPayloadBuilderNew()
                .build();

        String payloadbody =mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);

        JobPayloadBuilder jobpayloadbuilderReponse = RestAssured
                .given()
                .spec(OAuth2TokenManager.getRequestSpec())
                .body(payloadbody)
                .when()
                .post("/protected/resource")
                .then()
                .statusCode(200)
                .extract()
                .as(JobPayloadBuilder.class);

        assertEquals("Berlin",jobpayloadbuilderReponse.getTitle());







    }

    public static Map<String, Object> retrieveJWTtoken() {
        Map<String, Object> claims = JwtUtils.getClaims(OAuth2TokenManager.getAccessToken());
        System.out.println("Expiry: " + claims.get("exp"));
        System.out.println("Subject: " + claims.get("sub"));
        return claims;
    }


}
