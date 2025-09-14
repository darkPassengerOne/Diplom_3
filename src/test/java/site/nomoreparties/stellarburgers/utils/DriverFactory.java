package site.nomoreparties.stellarburgers.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Locale;

public class DriverFactory {

    @Step("Создание драйвера для браузера: {browser}")
    public static WebDriver create(String browser) {
        if ("yandex".equalsIgnoreCase(browser)) {
            return createYandexDriver();
        } else if ("chrome".equalsIgnoreCase(browser)) {
            return createChromeDriver();
        } else {
            throw new IllegalArgumentException("Unknown browser: " + browser);
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--start-maximized",
                "--disable-notifications",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );

        WebDriver driver = new ChromeDriver(options);
        tuneTimeouts(driver);
        return driver;
    }

    private static WebDriver createYandexDriver() {
        // 1) Путь к бину Яндекс.Браузера
        String yandexBin = resolveYandexBinary();
        System.out.println("[YA] binary = " + yandexBin);

        // 2) Жестко указал путь, к chromedriver-138 потому что иначе скачивается версия 140 для ЯБ и тесты падают.
        String driverPath = "/Users/nikolajivankov/Documents/Practicum/chromedriver-138/chromedriver";
        System.setProperty("webdriver.chrome.driver", driverPath);
        System.out.println("[YA] using chromedriver = " + driverPath);

        // 3) Опции и запуск
        ChromeOptions options = new ChromeOptions();
        options.setBinary(yandexBin);
        options.addArguments(
                "--start-maximized",
                "--disable-notifications",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--remote-allow-origins=*"
        );

        WebDriver driver = new ChromeDriver(options);
        tuneTimeouts(driver);
        return driver;
    }

    private static String resolveYandexBinary() {
        String yandexBin = System.getProperty("yandex.binary");
        if (yandexBin != null && !yandexBin.isBlank()) return yandexBin;

        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (os.contains("mac")) {
            return "/Applications/Yandex.app/Contents/MacOS/Yandex";
        } else if (os.contains("win")) {
            return System.getProperty("user.home")
                    + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";
        } else if (os.contains("linux")) {
            return "/usr/bin/yandex-browser";
        } else {
            throw new IllegalStateException("Неизвестная ОС. Укажи путь к Яндекс.Браузеру через -Dyandex.binary");
        }
    }

    private static void tuneTimeouts(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }
}