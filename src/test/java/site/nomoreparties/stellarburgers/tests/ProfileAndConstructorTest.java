package site.nomoreparties.stellarburgers.tests;

import io.qameta.allure.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pages.*;
import site.nomoreparties.stellarburgers.pages.components.Header;
import site.nomoreparties.stellarburgers.utils.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers")
@Feature("Навигация")
public class ProfileAndConstructorTest {

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

    @ParameterizedTest(name = "Переход в личный кабинет из хедера (незалогиненный) [{0}]")
    @ValueSource(strings = {"chrome","yandex"})
    @Description("Проверяем, что при клике на 'Личный кабинет' незалогиненный пользователь попадает на форму логина")
    void goToProfile(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        ApiUser user = ensureUser();

        try {
            new MainPage(driver).open();
            new Header(driver).goToAccount();
            assertTrue(new LoginPage(driver).isOpened(),
                    "Вместо логина открылась другая страница");
        } finally {
            cleanup(user);
            driver.quit();
        }
    }

    @ParameterizedTest(name = "Навигация: профиль → конструктор → профиль → логотип [{0}]")
    @ValueSource(strings = {"chrome","yandex"})
    @Description("Проверяем переход по клику на личный кабинет и переход из личного кабинета по клику на «Конструктор» и на логотип Stellar Burgers.")
    void backToConstructorViaButtonAndLogo(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        ApiUser user = ensureUser();

        try {
            // логинимся
            new MainPage(driver).open();
            new Header(driver).goToAccount();
            new LoginPage(driver)
                    .typeEmail(user.email)
                    .typePassword(user.password)
                    .submit();

            // после логина мы на главной → идём в профиль
            new Header(driver).goToAccount();
            ProfilePage profile = new ProfilePage(driver);
            assertTrue(profile.isOpened(), "Профиль не открылся после логина");

            // теперь из профиля в конструктор
            new Header(driver).goToConstructor();
            assertTrue(new MainPage(driver).isOpened(), "Конструктор (главная) не открылся по кнопке");

            // снова идём в профиль
            new Header(driver).goToAccount();
            assertTrue(profile.isOpened(), "Профиль не открылся повторно");

            // кликаем по логотипу Stellar Burgers
            new Header(driver).clickLogo();
            assertTrue(new MainPage(driver).isOpened(), "Главная не открылась по клику на логотип");
        } finally {
            cleanup(user);
            driver.quit();
        }
    }
}