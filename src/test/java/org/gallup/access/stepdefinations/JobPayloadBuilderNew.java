package org.gallup.access.stepdefinations;

public class JobPayloadBuilderNew {

    private String title;
    private String location;
    private int salary;

    public JobPayloadBuilderNew setTitle(String title) {
        this.title = title;
        return this;
    }

    public JobPayloadBuilderNew setLocation(String location) {
        this.location = location;
        return this;
    }

    public JobPayloadBuilderNew setSalary(int salary) {
        this.salary = salary;
        return this;
    }

    public JobPayload build() {
        return new JobPayload(title, location, salary);
    }



}

