package site.nomoreparties.stellarburgers.tests;

import io.qameta.allure.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pages.ConstructorPage;
import site.nomoreparties.stellarburgers.pages.MainPage;
import site.nomoreparties.stellarburgers.pages.components.Header;
import site.nomoreparties.stellarburgers.utils.DriverFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers")
@Feature("Конструктор - вкладки")
public class ConstructorTabsTest {

    @ParameterizedTest(name = "Переключение вкладок 'Булки/Соусы/Начинки' [{0}]")
    @ValueSource(strings = {"chrome","yandex"})
    @Description("Проверяем, что при клике по табам 'Булки', 'Соусы', 'Начинки' происходит переключение активной вкладки")
    void tabsSwitchingWorks(String browser) {
        WebDriver driver = DriverFactory.create(browser);
        try {
            new MainPage(driver).open();
            new Header(driver).goToConstructor();

            ConstructorPage cp = new ConstructorPage(driver);

            cp.openSauces();
            assertTrue(cp.saucesActive(), "Вкладка 'Соусы' не активна после клика");

            cp.openFillings();
            assertTrue(cp.fillingsActive(), "Вкладка 'Начинки' не активна после клика");

            cp.openBuns();
            assertTrue(cp.bunsActive(), "Вкладка 'Булки' не активна после клика");

        } finally {
            driver.quit();
        }
    }
}