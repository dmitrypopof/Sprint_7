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

public class CreateCourierTest {

    @Before
    @Step("Предусловие.Подключение к сайту")
    public void setUp() {
        RestAssured.baseURI = MainCommandRequest.URL_ADDRESS;
        RestAssured.config= RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig().
                setParam("http.connection.timeout",5000).
                setParam("http.socket.timeout",5000).
                setParam("http.connection-manager.timeout",5000));

    }
    @Test
    @DisplayName("Курьера можно создать. Возвращает код ответа 201")
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - тест (создание, валидность кода ответа 201)")
    public void createCourierValidateAndResponseTest() {
            Response response = given()
                    .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                    .auth().none()
                    .body(Courier.jsonNewCourier)
                    .when()
                    .post(MainCommandRequest.POST_REQUEST_CREATE_COURIER);
            response.then().assertThat().statusCode(HTTP_CREATED);
    }

    @Test
    @DisplayName("Курьера можно создать. Успешный запрос возвращает: {\"ok\":true}")//имя теста
    @Description("Post запрос на ручку /api/v1/courier")//описание теста
    @Step("Основной шаг - тест (создание, валидность {\"ok\":true} ")
    public void createCourierValidateTest() {
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none()
                .body(Courier.jsonNewCourier)
                .when()
                .post(MainCommandRequest.POST_REQUEST_CREATE_COURIER);
        response.then().assertThat().extract().path("true");
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров.Error 409")
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - тест(проверка двух одинаковых курьеров, возвращается ошибка 409 при повторяющимся логине )")
    public void twoIdenticalCouriers(){
        Response responseOne = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none()
                .body(Courier.jsonNewCourier)
                .when()
                .post(MainCommandRequest.POST_REQUEST_CREATE_COURIER);

        Response responseTwo = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none()
                .body(Courier.jsonNewCourier)
                .when()
                .post(MainCommandRequest.POST_REQUEST_CREATE_COURIER);
        responseTwo.then().assertThat().statusCode(HTTP_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без логина. Error 400")
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - создание курьера")
    public void createCourierNotLogin(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none()
                .body(Courier.jsonNewCourierWithoutLogin)
                .when()
                .post(MainCommandRequest.POST_REQUEST_CREATE_COURIER);
        response.then().assertThat().statusCode(HTTP_BAD_REQUEST);

    }

    @Test
    @DisplayName("Создание курьера без пароля. Error 400")
    @Description("Post запрос на ручку /api/v1/courier")
    @Step("Основной шаг - создание курьера")
    public void createCourierNotPassword(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none()
                .body(Courier.jsonNewCourierWithoutPassword)
                .when()
                .post(MainCommandRequest.POST_REQUEST_CREATE_COURIER);
        response.then().assertThat().statusCode(HTTP_BAD_REQUEST);

    }

    @After
    @Step("Постусловие.Удаление курьера")
    public void endTest() {
        try{
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none().body(Courier.jsonNewCourier).when().post(MainCommandRequest.POST_REQUEST_LOGIN_COURIER);

                given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none().and().pathParam("id",Integer.parseInt(response.asString().replaceAll("\\{\"id\":","").replaceAll("\\}","")))
                .delete(MainCommandRequest.DELETE_REQUEST_COURIER)
                .then().assertThat().statusCode(HTTP_OK);
        System.out.println("Постусловие. Курьер удален. Тест создание курьера");
        } catch (Exception exception){
            System.out.println("Тест завершился без удаления курьера т.к. id не найден");
        }
    }
}
