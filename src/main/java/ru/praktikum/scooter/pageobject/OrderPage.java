package ru.praktikum.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Страница заказа, шаг 1: «Для кого самокат»
public class OrderPage {

    private final WebDriver driver;

    // Поле «Имя»
    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");

    // Поле «Фамилия»
    private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");

    // Поле «Адрес: куда привезти заказ»
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле «Станция метро»
    private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");

    // Станция метро в выпадающем списке (ищем по названию)
    private static final String METRO_OPTION = ".//*[text()='%s']";

    // Поле «Телефон: на него позвонит курьер»
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка «Далее»
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoad() {
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(nameField));
    }

    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void setSurname(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    public void setAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void selectMetro(String station) {
        driver.findElement(metroField).click();
        // вводим название, чтобы в списке осталась только нужная станция
        driver.findElement(metroField).sendKeys(station);
        // список станций подгружается с сервера — ждём нужную станцию и кликаем по ней
        By option = By.xpath(String.format(METRO_OPTION, station));
        WebElement element = new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(option));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void setPhone(String phone) {
        // после выбора станции форма перерисовывается — ждём, пока поле телефона снова появится
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(phoneField));
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNext() {
        driver.findElement(nextButton).click();
    }

    // Шаг: заполнить форму «Для кого самокат» и нажать «Далее»
    public void fillCustomerForm(String name, String surname, String address, String station, String phone) {
        waitForLoad();
        setName(name);
        setSurname(surname);
        setAddress(address);
        selectMetro(station);
        setPhone(phone);
        clickNext();
    }
}