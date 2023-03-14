package ru.yandex.praktikum.model;

public class CredentialsForLoginNotPassword {
    private String login;

    public CredentialsForLoginNotPassword(String login) {
        this.login = login;
    }
    public static CredentialsForLoginNotPassword from (Courier courier){
        return new CredentialsForLoginNotPassword(courier.getLogin());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
