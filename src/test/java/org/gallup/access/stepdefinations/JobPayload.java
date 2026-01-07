package org.gallup.access.stepdefinations;


public class JobPayload {

    private String title;
    private String location;
    private int salary;

    public JobPayload() {}

    public JobPayload(String title, String location, int salary) {
        this.title = title;
        this.location = location;
        this.salary = salary;
    }

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public int getSalary() { return salary; }

    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setSalary(int salary) { this.salary = salary; }
}
