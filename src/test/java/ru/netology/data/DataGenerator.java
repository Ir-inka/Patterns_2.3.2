package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(JSON)
            .setContentType(JSON)
            .log(ALL)
            .build();
    public static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {

    }

    private static void sendRequest(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomUserName() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {

        }

        public static UserInfo getNewUser(String status) {
            return new UserInfo(getRandomUserName(), getRandomPassword(), status);
        }

        public static UserInfo getRegisteredUser(String status) {
            var registeredUser = getNewUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }

}
