import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginCourierTest {
    @Before
    @Step("Предусловие.Подключение к сайту, создание курьера")
    public void setUp(){
        RestAssured.baseURI = MainCommandRequest.URL_ADDRESS;
        RestAssured.config= RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig().
                setParam("http.connection.timeout",5000).
                setParam("http.socket.timeout",5000).
                setParam("http.connection-manager.timeout",5000));

        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none()
                .body(Courier.jsonNewCourier)
                .when()
                .post(MainCommandRequest.POST_REQUEST_CREATE_COURIER);
        response.then().assertThat().statusCode(HTTP_CREATED);
    }

    @Test
    @DisplayName("Курьер может авторизоваться.Ответ 200")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanAuthorizations(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none().body(Courier.jsonNewCourier)
                .when()
                .post(MainCommandRequest.POST_REQUEST_LOGIN_COURIER);
        response.then().assertThat().statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Курьер может авторизоваться. Запрос возвращает id")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanAuthorizationsReturnId(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none().body(Courier.jsonNewCourier)
                .when()
                .post(MainCommandRequest.POST_REQUEST_LOGIN_COURIER);
        response.then().assertThat().statusCode(HTTP_OK);
        response.then().assertThat().body("id",notNullValue());
    }

    @Test
    @DisplayName("Курьер не сможет авторизоваться, без обязательного поля - нет логина. Error 400")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanNotAuthorizationsWithoutLogin(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none().body(Courier.jsonNewCourierWithoutLogin)
                .when()
                .post(MainCommandRequest.POST_REQUEST_LOGIN_COURIER);
        response.then().assertThat().statusCode(HTTP_BAD_REQUEST)
                .and().assertThat().extract().path("message","Недостаточно данных для входа");

    }

    @Test
    @DisplayName("Курьер не сможет авторизоваться, без обязательного поля - нет пароля. Error 400")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanNotAuthorizationsWithoutPassword(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none().body(Courier.jsonNewCourierWithoutPassword)
                .when()
                .post(MainCommandRequest.POST_REQUEST_LOGIN_COURIER);
        response.then().assertThat().statusCode(HTTP_BAD_REQUEST)
                .and().assertThat().extract().path("message","Недостаточно данных для входа");
    }


    @Test
    @DisplayName("Курьер не сможет авторизоваться, неправильный логин. Error 404")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanNotAuthorizationsIncorrectLogin(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none().body(Courier.jsonNewCourierIncorrectLogin)
                .when()
                .post(MainCommandRequest.POST_REQUEST_LOGIN_COURIER);
        response.then().assertThat().statusCode(HTTP_NOT_FOUND)
                .and().assertThat().extract().path("message","Учетная запись не найдена");

    }

    @Test
    @DisplayName("Курьер не сможет авторизоваться, неправильный пароль. Error 404")
    @Description("Post запрос на ручку /api/v1/courier/login")
    @Step("Основной шаг - тест логин курьера")
    public void courierCanNotAuthorizationsIncorrectPassword(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none().body(Courier.jsonNewCourierIncorrectPassword)
                .when()
                .post(MainCommandRequest.POST_REQUEST_LOGIN_COURIER);
        response.then().assertThat().statusCode(HTTP_NOT_FOUND)
                .and().assertThat().extract().path("message","Учетная запись не найдена");
    }

    @After
    @Step("Постусловие.Удаление курьера")
    public void endTest(){
        try{
            Response response = given()
                    .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                    .auth().none().body(Courier.jsonNewCourier).when().post(MainCommandRequest.POST_REQUEST_LOGIN_COURIER);

            given()
                    .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                    .auth().none().and().pathParam("id",Integer.parseInt(response.asString().replaceAll("\\{\"id\":","").replaceAll("\\}","")))
                    .delete(MainCommandRequest.DELETE_REQUEST_COURIER);
                    System.out.println("Постусловие. Курьер удален. Тест логин курьера ");
        } catch (Exception exception){
            System.out.println("Тест завершился без удаления курьера т.к. id не найден. Тест логин курьера");
        }
    }

}
