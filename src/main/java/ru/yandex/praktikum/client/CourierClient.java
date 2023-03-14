package ru.yandex.praktikum.client;

import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.model.*;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient{
    private static final String COURIER_URI = BASE_URI + "courier/";


   public ValidatableResponse create(Courier courier) {
       return given()
               .spec(getBaseReqSpec())
               .body(courier)
               .when()
               .post(COURIER_URI)
               .then();
   }
    public ValidatableResponse createNotPassword(CourierNotPassword courierNotPassword) {
        return given()
                .spec(getBaseReqSpec())
                .body(courierNotPassword)
                .when()
                .post(COURIER_URI)
                .then();
    }
    public ValidatableResponse createNotLogin(CourierNotLogin courierNotLogin) {
        return given()
                .spec(getBaseReqSpec())
                .body(courierNotLogin)
                .when()
                .post(COURIER_URI)
                .then();
    }

    public ValidatableResponse delete(int id) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .delete(COURIER_URI + id)
                .then();
    }

    public ValidatableResponse login (CredentialsForLogin credentialsForLogin){
        return given()
                .spec(getBaseReqSpec())
                .body(credentialsForLogin)
                .when()
                .post(COURIER_URI+ "login/")
                .then();
    }
    public ValidatableResponse loginNotLog (CredentialsForLoginNotLogin credentialsForLoginNotLogin){
        return given()
                .spec(getBaseReqSpec())
                .body(credentialsForLoginNotLogin)
                .when()
                .post(COURIER_URI+ "login/")
                .then();
    }

    public ValidatableResponse loginNotPass (CredentialsForLoginNotPassword credentialsForLoginNotPassword){
        return given()
                .config(RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig() // немного подкрутил запрос для удобства
                .setParam("http.socket.timeout",5000)))
                .spec(getBaseReqSpec())
                .body(credentialsForLoginNotPassword)
                .when()
                .post(COURIER_URI+ "login/")
                .then();
    }

    public ValidatableResponse loginErrorLog (CredentialsForLoginErrorLog credentialsForLoginErrorLog){
        return given()
                .spec(getBaseReqSpec())
                .body(credentialsForLoginErrorLog)
                .when()
                .post(COURIER_URI+ "login/")
                .then();
    }

    public ValidatableResponse loginErrorPass (CredentialsForLoginErrorPassword credentialsForLoginErrorPassword){
        return given()
                .spec(getBaseReqSpec())
                .body(credentialsForLoginErrorPassword)
                .when()
                .post(COURIER_URI+ "login/")
                .then();
    }

    public ValidatableResponse getListOfOrder (){
        return given()
                .spec(getBaseReqSpec())
                .when()
                .get(BASE_URI+ "orders/")
                .then();
    }

    public ValidatableResponse createOrder (ListForCreateOrder listForCreateOrder){
        return given()
                .spec(getBaseReqSpec())
                .body(listForCreateOrder)
                .when()
                .post(BASE_URI+ "orders/")
                .then();
    }





}
