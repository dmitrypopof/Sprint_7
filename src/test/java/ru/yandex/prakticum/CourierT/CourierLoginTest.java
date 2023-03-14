package ru.yandex.prakticum.CourierT;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.CourierGenerator;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.*;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private CourierClient courierClient;
    private int courierId;
    @Before
    public void setUp(){
        courierClient = new CourierClient();

    }

    @Test
    @DisplayName("Курьер может авторизоваться.Ответ 200")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanAuthorizations(){
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CredentialsForLogin.from(courier));
        loginResponse.assertThat().statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Курьер может авторизоваться. Запрос возвращает id")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanAuthorizationsReturnId(){
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CredentialsForLogin.from(courier));
        loginResponse.assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Курьер не сможет авторизоваться, без обязательного поля - нет логина. Error 400")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanNotAuthorizationsWithoutLogin(){
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.loginNotLog(CredentialsForLoginNotLogin.from(courier));
        loginResponse.assertThat().statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Курьер не сможет авторизоваться, без обязательного поля - нет пароля. Error 400")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanNotAuthorizationsWithoutPassword(){
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.loginNotPass(CredentialsForLoginNotPassword.from(courier));
        loginResponse.assertThat().statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Курьер не сможет авторизоваться, неправильный логин. Error 404")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanNotAuthorizationsIncorrectLogin(){
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.loginErrorLog(CredentialsForLoginErrorLog.from(courier));
        loginResponse.assertThat().statusCode(HTTP_NOT_FOUND)
                .and()
                .assertThat().extract().path("message","Учетная запись не найдена");

    }

    @Test
    @DisplayName("Курьер не сможет авторизоваться, неправильный пароль. Error 404")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanNotAuthorizationsIncorrectPassword(){
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.loginErrorPass(CredentialsForLoginErrorPassword.from(courier));
        loginResponse.assertThat().statusCode(HTTP_NOT_FOUND)
                .and()
                .assertThat().extract().path("message","Учетная запись не найдена");
    }


    @After
    @Step("Постусловие.Удаление курьера")
    public void clearData() {
        courierClient.delete(courierId);
        System.out.println("Курьер удален");
    }
}
