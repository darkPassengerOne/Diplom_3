package site.nomoreparties.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.utils.Waiter;

public class ForgotPasswordPage {
    private final WebDriver driver;
    private final Waiter ui;

    private final By loginLink = By.cssSelector("a[href='/login']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.ui = new Waiter(driver);
    }

    @Step("Перейти к логину со страницы восстановления пароля")
    public void goToLogin() { ui.click(loginLink); }
}