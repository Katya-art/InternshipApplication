package ua.kpi.comsys.internshipapplication.models;

public class Contact {
    private String name;
    private String email;
    private String status;

    public Contact(String name, String email, String status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainInfo() {
        return name + "\n" + status + "\n" + email;
    }
}
