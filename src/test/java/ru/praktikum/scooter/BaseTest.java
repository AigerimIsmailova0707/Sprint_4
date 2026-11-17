package ru.praktikum.scooter;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.scooter.pageobject.MainPage;

// Общая настройка для всех тестов: запуск браузера и его закрытие
public abstract class BaseTest {

    protected WebDriver driver;
    protected MainPage mainPage;

    @Before
    public void setUp() {
        // По умолчанию Chrome. Для Firefox: mvn test -Dbrowser=firefox
        String browser = System.getProperty("browser", "chrome");
        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
        }

        mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}