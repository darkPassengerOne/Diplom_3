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
@Feature("Выход")
public class LogoutTest {

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

    @ParameterizedTest(name = "Выход по кнопке 'Выход' в личном кабинете [{0}]")
    @ValueSource(strings = {"chrome","yandex"})
    @Description("Логинимся в личный кабинет, затем выходим через кнопку 'Выход' и проверяем возврат на форму входа")
    void logoutFromProfile(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        ApiUser user = ensureUser();

        try {
            // шаг 1-3: логинимся
            new MainPage(driver).open();
            new Header(driver).goToAccount();
            new LoginPage(driver)
                    .typeEmail(user.email)
                    .typePassword(user.password)
                    .submit();

            // шаг 4: на главной -> снова идём в личный кабинет
            new Header(driver).goToAccount();

            // шаг 5: жмём "Выход"
            ProfilePage profile = new ProfilePage(driver);
            assertTrue(profile.isOpened(), "Не удалось открыть личный кабинет после логина");
            profile.logout();

            // шаг 6: проверяем, что открыта форма входа
            assertTrue(new LoginPage(driver).isOpened(),
                    "После выхода не открылась страница логина");
        } finally {
            cleanup(user);
            driver.quit();
        }
    }
}