package site.nomoreparties.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By bunsTab     = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesTab   = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']/parent::div");

    private final String activeClass = "tab_tab_type_current__2BEPc";

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Открыть вкладку 'Булки'")
    public void openBuns() { driver.findElement(bunsTab).click(); }

    @Step("Открыть вкладку 'Соусы'")
    public void openSauces() { driver.findElement(saucesTab).click(); }

    @Step("Открыть вкладку 'Начинки'")
    public void openFillings() { driver.findElement(fillingsTab).click(); }

    @Step("Проверить, что вкладка 'Булки' активна")
    public boolean bunsActive() {
        WebElement el = driver.findElement(bunsTab);
        wait.until(ExpectedConditions.attributeContains(el, "class", activeClass));
        return el.getAttribute("class").contains(activeClass);
    }

    @Step("Проверить, что вкладка 'Соусы' активна")
    public boolean saucesActive() {
        WebElement el = driver.findElement(saucesTab);
        wait.until(ExpectedConditions.attributeContains(el, "class", activeClass));
        return el.getAttribute("class").contains(activeClass);
    }

    @Step("Проверить, что вкладка 'Начинки' активна")
    public boolean fillingsActive() {
        WebElement el = driver.findElement(fillingsTab);
        wait.until(ExpectedConditions.attributeContains(el, "class", activeClass));
        return el.getAttribute("class").contains(activeClass);
    }
}