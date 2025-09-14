package site.nomoreparties.stellarburgers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    public static final String URL = "https://stellarburgers.nomoreparties.site/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    // локаторы
    private final By loginButtonOnMain = By.xpath("//button[contains(text(),'Войти в аккаунт')]");
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public MainPage open() {
        driver.get(URL);
        return this;
    }

    /** Клик по кнопке "Войти в аккаунт" на главной */
    public void clickLoginOnMain() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonOnMain)).click();
    }

    /** Проверка, что открыта главная страница конструктора */
    public boolean isOpened() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15)) // здесь увеличили
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//button[normalize-space(text())='Оформить заказ']")
                    ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}