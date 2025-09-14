package site.nomoreparties.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pages.LoginPage;
import site.nomoreparties.stellarburgers.pages.MainPage;
import site.nomoreparties.stellarburgers.pages.RegisterPage;
import site.nomoreparties.stellarburgers.utils.DriverFactory;
import site.nomoreparties.stellarburgers.utils.UserGenerator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers")
@Feature("Регистрация")
public class RegistrationTest {

    @ParameterizedTest(name = "Успешная регистрация [{0}]")
    @ValueSource(strings = {"chrome", "yandex"})
    @Description("Проверяем успешную регистрацию и редирект на страницу входа (форма логина)")
    void successfulRegistration(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        String email = "ui-" + UUID.randomUUID() + "@yandex.ru";
        String pass = "secret1"; // >= 6 символов
        String name = "UI Tester";

        try {
            new MainPage(driver).open();
            new site.nomoreparties.stellarburgers.pages.components.Header(driver).goToAccount(); // редирект на /login

            new LoginPage(driver).goToRegister();

            new RegisterPage(driver)
                    .typeName(name)
                    .typeEmail(email)
                    .typePassword(pass)
                    .submit();

            // Проверяем, что снова открылась форма входа
            assertTrue(new LoginPage(driver).isOpened(),
                    "После регистрации не открылась форма входа");

        } finally {
            // чистим созданного пользователя
            String token = UserGenerator.login(email, pass);
            UserGenerator.deleteUser(token);
            driver.quit();
        }
    }

    @ParameterizedTest(name = "Ошибка регистрации при коротком пароле [{0}]")
    @ValueSource(strings = {"chrome", "yandex"})
    @Description("Проверяем валидацию минимальной длины пароля (<6 символов)")
    void registrationShortPasswordError(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        String email = "ui-" + UUID.randomUUID() + "@yandex.ru";
        String pass = "12345"; // 5 символов
        String name = "UI Tester";

        try {
            new MainPage(driver).open();
            new site.nomoreparties.stellarburgers.pages.components.Header(driver).goToAccount();
            new LoginPage(driver).goToRegister();

            RegisterPage reg = new RegisterPage(driver);
            reg.typeName(name).typeEmail(email).typePassword(pass).submit();

            assertTrue(reg.isShortPasswordErrorShown(),
                    "Не показана ошибка о некорректном пароле");

        } finally {
            driver.quit();
        }
    }
}