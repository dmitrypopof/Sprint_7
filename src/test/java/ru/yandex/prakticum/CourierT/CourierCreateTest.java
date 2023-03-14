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
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.model.CourierNotLogin;
import ru.yandex.praktikum.model.CourierNotPassword;

import static java.net.HttpURLConnection.*;

public class CourierCreateTest {
    private CourierClient courierClient;
    private int courierId;
    @Before
    public void setUp() {
        courierClient = new CourierClient();

    }

    @Test
    @DisplayName("Курьера можно создать. Возвращает код ответа 201")
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - тест (создание, валидность кода ответа 201)")
    public void createCourierValidateAndResponseTest(){
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse.assertThat().statusCode(HTTP_CREATED);
    }

    @Test
    @DisplayName("Курьера можно создать. Успешный запрос возвращает: {\"ok\":true}")//имя теста
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - тест (создание, валидность {\"ok\":true} ")
    public void createCourierValidateTest() {
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse.assertThat().extract().path("true");
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров.Error 409")
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - тест(проверка двух одинаковых курьеров, возвращается ошибка 409 при повторяющимся логине )")
    public void twoIdenticalCouriers(){
        Courier courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse createResponseSecond = courierClient.create(courier);
        createResponseSecond.assertThat().statusCode(HTTP_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без пароля. Error 400")
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - создание курьера")
    public void createCourierNotPassword(){
        CourierNotPassword courierNotPassword = CourierGenerator.getRandomNotPassword();
        ValidatableResponse createResponse = courierClient.createNotPassword(courierNotPassword);
        createResponse.assertThat().statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без логина. Error 400")
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - создание курьера")
    public void createCourierNotLogin(){
        CourierNotLogin courierNotLogin = CourierGenerator.getRandomNotLogin();
        ValidatableResponse createResponse = courierClient.createNotLogin(courierNotLogin);
        createResponse.assertThat().statusCode(HTTP_BAD_REQUEST);

    }
    @After
    @Step("Постусловие.Удаление курьера")
    public void clearData() {
        courierClient.delete(courierId);
        System.out.println("Курьер удален");

    }
}
