package org.gallup.access.mockAPIs;

import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ApplePayMock {
    public void setupStub() {
        stubFor(post(urlPathEqualTo("/applepay/payment"))
                .withRequestBody(matchingJsonPath("$.amount", equalTo("100.00")))
                .withRequestBody(matchingJsonPath("$.currency", equalTo("EUR")))
                .withRequestBody(matchingJsonPath("$.token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"transactionId\": \"TXN123456\",\n" +
                                "  \"status\": \"SUCCESS\",\n" +
                                "  \"message\": \"Payment processed via Apple Pay\"\n" +
                                "}")));
    }

    @Test
    public void testApplePayPaymentSuccess() {
        String baseUrl = "http://localhost:8080/applepay/payment";

        String requestBody = "{\n" +
                "  \"amount\": \"100.00\",\n" +
                "  \"currency\": \"EUR\",\n" +
                "  \"token\": \"mocked-applepay-token\"\n" +
                "}";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(baseUrl);

        // Validate mocked response
        response.then().statusCode(200);
        response.then().body("status", (ResponseAwareMatcher<Response>) equalTo("SUCCESS"));
        response.then().body("transactionId", (ResponseAwareMatcher<Response>) equalTo("TXN123456"));
    }
}