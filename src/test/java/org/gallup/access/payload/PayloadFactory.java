package org.gallup.access.payload;

public class PayloadFactory {

    public static String getPayload(String type) {
        switch (type.toLowerCase()) {
            case "createjob":
                return "{ \"title\": \"QA Engineer\", \"location\": \"Berlin\" }";

            case "updatejob":
                return "{ \"title\": \"Senior QA Engineer\" }";

            default:
                throw new IllegalArgumentException("Invalid payload type");
        }
    }
    public static JobPayloadBuilder getJobBuilder() {
        return new JobPayloadBuilder();
    }
    String payload = PayloadFactory.getPayload("createjob");
    JobPayloadBuilder builder = PayloadFactory.getJobBuilder();
    String payload1 = builder.setSalary(90000).build();





//    “For simple payloads, I use the Factory pattern.
//    For dynamic or complex payloads, I use the Builder pattern.
//    In some cases, the Factory returns a Builder instance to allow further customization.”




}
