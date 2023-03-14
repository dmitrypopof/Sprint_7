package ru.yandex.praktikum.model;

public class CredentialsForLoginErrorPassword {
    private String login;
    private String password;

    public CredentialsForLoginErrorPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static CredentialsForLoginErrorPassword from(Courier courier) {
        return new CredentialsForLoginErrorPassword(courier.getLogin(), "1234:=)");
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
