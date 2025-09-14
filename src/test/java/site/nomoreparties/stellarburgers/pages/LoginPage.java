package site.nomoreparties.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.utils.Waiter;

public class LoginPage {
    private final WebDriver driver;
    private final Waiter ui;

    private final By formRoot      = By.xpath("//form[contains(@class,'Auth_form')]");
    private final By emailInput    = By.xpath("//label[normalize-space()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[normalize-space()='Пароль']/following-sibling::input");
    private final By submitBtn     = By.xpath("//form//button[normalize-space()='Войти']");

    private final By registerLink  = By.cssSelector("a[href='/register']");
    private final By forgotLink    = By.cssSelector("a[href='/forgot-password']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.ui = new Waiter(driver);
    }

    @Step("Страница логина открыта")
    public boolean isOpened() { return ui.exists(formRoot); }

    @Step("Ввести email: {email}")
    public LoginPage typeEmail(String email) { ui.type(emailInput, email); return this; }

    @Step("Ввести пароль")
    public LoginPage typePassword(String password) { ui.type(passwordInput, password); return this; }

    @Step("Отправить форму входа")
    public void submit() { ui.click(submitBtn); }

    @Step("Перейти на страницу регистрации по ссылке")
    public void goToRegister() { ui.click(registerLink); }

    @Step("Перейти на страницу восстановления пароля по ссылке")
    public void goToForgot() { ui.click(forgotLink); }
}