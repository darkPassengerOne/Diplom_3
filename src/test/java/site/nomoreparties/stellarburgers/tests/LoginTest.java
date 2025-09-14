package site.nomoreparties.stellarburgers.tests;

import io.qameta.allure.*;
import io.qameta.allure.Description;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pages.*;
import site.nomoreparties.stellarburgers.pages.components.Header;
import site.nomoreparties.stellarburgers.utils.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers")
@Feature("Вход")
public class LoginTest {

    private ApiUser ensureUser() {
        String email = "ui-" + UUID.randomUUID() + "@yandex.ru";
        String pass = "secret1";
        ApiUser user = new ApiUser(email, pass, "UI Tester");
        UserGenerator.register(user).then().statusCode(200);
        return user;
    }

    private void cleanup(ApiUser u) {
        String token = UserGenerator.login(u.email, u.password);
        UserGenerator.deleteUser(token);
    }


    @ParameterizedTest(name = "Вход с главной кнопкой 'Войти в аккаунт' [{0}]")
    @Description("Вход с главной кнопкой 'Войти в аккаунт'")
    @ValueSource(strings = {"chrome", "yandex"})
    void loginFromMainButton(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        ApiUser user = ensureUser();

        try {
            MainPage main = new MainPage(driver).open();
            main.clickLoginOnMain();

            new LoginPage(driver)
                    .typeEmail(user.email)
                    .typePassword(user.password)
                    .submit();

            assertTrue(new MainPage(driver).isOpened(),
                    "После логина не открылась главная страница");
        } finally {
            cleanup(user);
            driver.quit();
        }
    }

    @ParameterizedTest(name = "Вход через 'Личный кабинет' в хедере [{0}]")
    @Description("Вход через 'Личный кабинет' в хедере")
    @ValueSource(strings = {"chrome", "yandex"})
    void loginFromHeaderAccount(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        ApiUser user = ensureUser();

        try {
            new MainPage(driver).open();
            new Header(driver).goToAccount(); // редирект на /login

            new LoginPage(driver)
                    .typeEmail(user.email)
                    .typePassword(user.password)
                    .submit();

            assertTrue(new MainPage(driver).isOpened(),
                    "После логина не открылась главная страница");
        } finally {
            cleanup(user);
            driver.quit();
        }
    }

    @ParameterizedTest(name = "Вход через ссылку 'Войти' на форме регистрации [{0}]")
    @Description("Вход через ссылку 'Войти' на форме регистрации")
    @ValueSource(strings = {"chrome", "yandex"})
    void loginViaRegisterForm(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        ApiUser user = ensureUser();

        try {
            driver.get(MainPage.URL + "register");
            new RegisterPage(driver).clickLoginLink();

            new LoginPage(driver)
                    .typeEmail(user.email)
                    .typePassword(user.password)
                    .submit();

            assertTrue(new MainPage(driver).isOpened(),
                    "После логина не открылась главная страница");
        } finally {
            cleanup(user);
            driver.quit();
        }
    }

    @ParameterizedTest(name = "Вход через ссылку 'Войти' на странице восстановления пароля [{0}]")
    @Description("Вход через ссылку 'Войти' на странице восстановления пароля")
    @ValueSource(strings = {"chrome", "yandex"})
    void loginViaForgotPassword(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        ApiUser user = ensureUser();

        try {
            driver.get(MainPage.URL + "forgot-password");
            new ForgotPasswordPage(driver).goToLogin();

            new LoginPage(driver)
                    .typeEmail(user.email)
                    .typePassword(user.password)
                    .submit();

            assertTrue(new MainPage(driver).isOpened(),
                    "После логина не открылась главная страница");
        } finally {
            cleanup(user);
            driver.quit();
        }
    }
}