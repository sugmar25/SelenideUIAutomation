package org.gallup.access.pojo;

public class Users {
    private int id;
    private String name;
    private String emailid;
    private String dept;
    private String role;

    public Users(int id, String name, String emailid, String dept, String role) {
        this.id = id;
        this.name = name;
        this.emailid = emailid;
        this.dept = dept;
        this.role = role;
    }

    public Users() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
