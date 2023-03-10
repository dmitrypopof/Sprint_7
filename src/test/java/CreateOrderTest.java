import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String firstname;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String color;

    public CreateOrderTest(String firstname, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String color) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getInput(){
        return new Object[][]{
                {"Фёдор","Достоевский","Старая Басманная 21","181","88002000600","2","2023-03-20","Самокат не наказание","[\"BLACK\",\"GREY\"]"},
                {"Владимир","Маяковский","Красная Пресня 36","30","88002000600","1","2023-03-21","Самокаты в облаках","[\"BLACK\"]"},
                {"Сергей","Есенин","Большой Строченовский 24","23","88002000600","2","2023-03-20","Самокат не наказание",null}

        };
    }

    @Before
    @Step("Предусловие.Подключение к сайту")
    public void setUp() {
        RestAssured.baseURI = MainCommandRequest.URL_ADDRESS;
        RestAssured.config= RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout",10000)
                .setParam("http.socket.timeout",10000)
                .setParam("http.connection-manager.timeout",10000));
    }

    @Test
    @DisplayName("Создание заказа с параметризацией")
    @Description("Post запрос на ручку /api/v1/orders")
    @Step("Основной шаг - тест(создание заказа)")
    public void createOrder(){
        Response response = given()
                .header(MainCommandRequest.HEADER_CONTENT_TYPE, MainCommandRequest.HEADER_APPL_JSON)
                .auth().none()
                .body("{\n" +
                        "    \"firstName\": \""+ firstname +"\",\n" +
                        "    \"lastName\": \""+ lastName +"\",\n" +
                        "    \"address\": \""+ address +"\",\n" +
                        "    \"metroStation\": "+metroStation+",\n" +
                        "    \"phone\": \""+phone+"\",\n" +
                        "    \"rentTime\": "+rentTime+",\n" +
                        "    \"deliveryDate\": \""+deliveryDate+"\",\n" +
                        "    \"comment\": \""+comment+"\",\n" +
                        "    \"color\": "+color+"\n" +
                        "}")
                .when()
                .post(MainCommandRequest.CREATE_ORDERS);
        response.then().assertThat().statusCode(201);
        response.then().assertThat().extract().path("track");
        //System.out.println(response.asString());
    }

}
