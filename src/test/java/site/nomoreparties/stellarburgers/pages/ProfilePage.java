package site.nomoreparties.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.utils.Waiter;

public class ProfilePage {
    private final WebDriver driver;
    private final Waiter ui;

    // вкладки и кнопки в профиле
    private final By profileTab   = By.linkText("Профиль");
    private final By ordersTab    = By.linkText("История заказов");
    private final By logoutButton = By.xpath("//button[normalize-space()='Выход']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.ui = new Waiter(driver);
    }

    @Step("Проверить, что страница профиля открыта")
    public boolean isOpened() {
        // ждём появления вкладки "Профиль"
        return ui.exists(profileTab, 5) && ui.exists(logoutButton, 5);
    }

    @Step("Выход из аккаунта")
    public void logout() {
        ui.click(logoutButton);
    }
}