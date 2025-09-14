package site.nomoreparties.stellarburgers.utils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserGenerator {

    static {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Step("Создать пользователя через API")
    public static Response register(ApiUser user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/register")
                .andReturn();
    }

    @Step("Логин пользователя через API (получить accessToken)")
    public static String login(String email, String password) {
        String body = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        Response resp = given()
                .header("Content-type", "application/json")
                .body(body)
                .post("/api/auth/login")
                .andReturn();

        return resp.then().extract().path("accessToken"); // "Bearer xxx"
    }

    @Step("Удалить пользователя через API")
    public static void deleteUser(String bearerToken) {
        if (bearerToken == null) return;
        given()
                .header("Authorization", bearerToken)
                .delete("/api/auth/user")
                .then().statusCode(202);
    }
}