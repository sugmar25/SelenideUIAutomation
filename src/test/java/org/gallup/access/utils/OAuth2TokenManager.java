package org.gallup.access.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OAuth2TokenManager {

    private static RequestSpecification requestSpec;

    public static String getAccessToken() {
        Response response = RestAssured
                .given()
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "your_client_id")
                .formParam("client_secret", "your_client_secret")
                .post("https://auth.yourdomain.com/oauth2/token");

        return response.jsonPath().getString("access_token");
    }

    // JWT Token generation steps
    public static RequestSpecification getRequestSpec() {
        if (requestSpec == null) {
            String token = getAccessToken();
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri("https://api.yourdomain.com")
                    .addHeader("Authorization", "Bearer " + token)
                    .setContentType("application/json")
                    .build();
        }
        return requestSpec;
    }

}

