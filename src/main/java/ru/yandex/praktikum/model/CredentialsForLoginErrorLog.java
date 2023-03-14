package ru.yandex.praktikum.model;

public class CredentialsForLoginErrorLog {
    private String login;
    private String password;

    public CredentialsForLoginErrorLog(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static CredentialsForLoginErrorLog from(Courier courier) {
        return new CredentialsForLoginErrorLog("1234:=)", courier.getPassword());
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
