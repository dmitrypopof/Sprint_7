package ru.yandex.praktikum.model;

public class CredentialsForLogin {
    private String login;
    private String password;

    public CredentialsForLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static CredentialsForLogin from(Courier courier) {
        return new CredentialsForLogin(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
