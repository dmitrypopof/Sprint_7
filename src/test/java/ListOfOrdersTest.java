import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.core.IsNull.notNullValue;

public class ListOfOrdersTest {
    @Before
    @Step("Предусловие.Подключение к сайту")
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        RestAssured.config= RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig().
                setParam("http.connection.timeout",10000).
                setParam("http.socket.timeout",10000).
                setParam("http.connection-manager.timeout",10000));

    }

    @Test
    @DisplayName("Получить список заказов. 200")//имя теста
    @Description("GET запрос на ручку /api/v1/orders")//описание теста
    @Step("Основной шаг - тест")
    public void getListOfOrder(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none()
                .when()
                .get("/api/v1/orders");
        response.then().assertThat().statusCode(HTTP_OK);
        response.then().assertThat().body("orders.id",notNullValue());
        System.out.println(response.asString());

    }

}
