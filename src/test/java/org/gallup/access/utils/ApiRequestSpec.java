package org.gallup.access.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ApiRequestSpec {

    public static RequestSpecification getSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.yourdomain.com")
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    public static RequestSpecification getEcho(){
        return new RequestSpecBuilder()
                .setBaseUri("https://postman-echo.com")
                .addHeader("Content-Type", "application/json")
                .build();
    }

}

