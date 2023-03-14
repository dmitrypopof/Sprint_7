package ru.yandex.praktikum.model;

public class CredentialsForLoginNotLogin {
    private String password;

    public CredentialsForLoginNotLogin(String password) {
        this.password = password;
    }
    public static CredentialsForLoginNotLogin from (Courier courier){
        return new CredentialsForLoginNotLogin(courier.getPassword());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
