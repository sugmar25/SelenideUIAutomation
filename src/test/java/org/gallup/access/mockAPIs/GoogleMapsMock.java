package org.gallup.access.mockAPIs;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.matching.UrlPathPattern;
import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.reflect.Array.get;

public class GoogleMapsMock {
    public void setupStub() {
        stubFor(get(urlPathEqualTo("/maps/api/directions/json"))
                .withQueryParam("origin", equalTo("Berlin"))
                .withQueryParam("destination", equalTo("Munich"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"routes\": [\n" +
                                "    {\n" +
                                "      \"summary\": \"A9\",\n" +
                                "      \"legs\": [\n" +
                                "        {\n" +
                                "          \"distance\": { \"text\": \"584 km\", \"value\": 584000 },\n" +
                                "          \"duration\": { \"text\": \"5 hours 30 mins\", \"value\": 19800 }\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}")));
    }

    private MappingBuilder get(UrlPathPattern urlPathPattern) {
        return null;
    }

    @Test
    public void testDeliveryEtaCalculation() {
        // Assume WireMock server is running on localhost:8080
        String baseUrl = "http://localhost:8080/maps/api/directions/json?origin=Berlin&destination=Munich";

        Response response = RestAssured.get(baseUrl);

        // Validate mocked response
        response.then().statusCode(200);
        response.then().body("routes[0].legs[0].distance.text", (ResponseAwareMatcher<Response>) equalTo("584 km"));
        response.then().body("routes[0].legs[0].duration.text", (ResponseAwareMatcher<Response>) equalTo("5 hours 30 mins"));
    }
}
