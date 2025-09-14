package site.nomoreparties.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.utils.Waiter;

public class RegisterPage {
    private final WebDriver driver;
    private final Waiter ui;

    private final By nameInput     = By.xpath("//label[normalize-space()='Имя']/following-sibling::input");
    private final By emailInput    = By.xpath("//label[normalize-space()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[normalize-space()='Пароль']/following-sibling::input");
    private final By submitBtn     = By.xpath("//form//button[normalize-space()='Зарегистрироваться']");
    private final By loginLink     = By.cssSelector("a[href='/login']");

    // Ошибка для короткого пароля
    //private final By shortPasswordError = By.xpath("//p[@class='input__error' and text()='Некорректный пароль']");
    // ищем <p> с классом input__error, в котором есть текст "Некорректный пароль"
    private final By shortPasswordError = By.xpath("//p[contains(@class,'input__error') and contains(text(),'Некорректный пароль')]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.ui = new Waiter(driver);
    }

    @Step("Ввести имя: {name}")
    public RegisterPage typeName(String name) { ui.type(nameInput, name); return this; }

    @Step("Ввести email: {email}")
    public RegisterPage typeEmail(String email) { ui.type(emailInput, email); return this; }

    @Step("Ввести пароль")
    public RegisterPage typePassword(String password) { ui.type(passwordInput, password); return this; }

    @Step("Отправить форму регистрации")
    public void submit() { ui.click(submitBtn); }

    @Step("Перейти к логину по ссылке внизу формы")
    public void clickLoginLink() { ui.click(loginLink); }

    @Step("Проверить, что показана ошибка короткого пароля")
    public boolean isShortPasswordErrorShown() {
        // ждём до 5 секунд появления ошибки
        return ui.exists(shortPasswordError, 5);
    }
}