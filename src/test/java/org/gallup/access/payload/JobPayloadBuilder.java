package org.gallup.access.payload;

public class JobPayloadBuilder {
    private String title;
    private String location;
    private int salary;

    public JobPayloadBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public JobPayloadBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public JobPayloadBuilder setSalary(int salary) {
        this.salary = salary;
        return this;
    }


    public String build() {
        return "{ \"title\": \"" + title + "\", \"location\": \"" + location + "\", \"salary\": " + salary + " }";
    }

    String payload = new JobPayloadBuilder()
            .setTitle("QA Engineer")
            .setLocation("Berlin")
            .setSalary(80000)
            .build();
}
