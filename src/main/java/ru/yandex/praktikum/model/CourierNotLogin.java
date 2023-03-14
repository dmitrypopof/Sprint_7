package ru.yandex.praktikum.model;

public class CourierNotLogin {
    private String password;
    private String firstName;

    public CourierNotLogin(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
