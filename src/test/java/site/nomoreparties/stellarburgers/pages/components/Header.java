package site.nomoreparties.stellarburgers.pages.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.utils.Waiter;

public class Header {
    private final WebDriver driver;
    private final Waiter ui;

    private final By constructorLink = By.linkText("Конструктор");
    private final By feedLink        = By.linkText("Лента Заказов");
    private final By logo            = By.cssSelector(".AppHeader_header__logo__2D0X2 a");
    private final By accountLink     = By.linkText("Личный Кабинет");

    public Header(WebDriver driver) {
        this.driver = driver;
        this.ui = new Waiter(driver);
    }

    @Step("Переход в 'Конструктор' через хедер")
    public void goToConstructor() { ui.click(constructorLink); }

    @Step("Переход в 'Ленту заказов' через хедер")
    public void goToFeed() { ui.click(feedLink); }

    @Step("Клик по логотипу (домой)")
    public void clickLogo() { ui.click(logo); }

    @Step("Переход в 'Личный кабинет' через хедер")
    public void goToAccount() { ui.click(accountLink); }
}