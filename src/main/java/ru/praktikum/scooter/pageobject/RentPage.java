package ru.praktikum.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Страница заказа, шаг 2: «Про аренду»
public class RentPage {

    private final WebDriver driver;

    // Поле «Когда привезти самокат»
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");

    // Поле «Срок аренды»
    private final By rentPeriodField = By.className("Dropdown-control");

    // Вариант срока аренды в выпадающем списке
    private static final String RENT_PERIOD_OPTION = ".//div[@class='Dropdown-option' and text()='%s']";

    // Чекбокс цвета самоката: id = black или grey
    private static final String COLOR_CHECKBOX_ID = "%s";

    // Поле «Комментарий для курьера»
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    // Кнопка «Заказать» под формой
    private final By orderButton = By.xpath(".//div[contains(@class,'Order_Buttons')]/button[text()='Заказать']");

    // Кнопка «Да» в окне «Хотите оформить заказ?»
    private final By confirmYesButton = By.xpath(".//div[contains(@class,'Order_Modal')]//button[text()='Да']");

    // Заголовок окна «Заказ оформлен»
    private final By successHeader = By.xpath(".//div[contains(@class,'Order_ModalHeader') and contains(text(),'Заказ оформлен')]");

    public RentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoad() {
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(dateField));
    }

    public void setDate(String date) {
        driver.findElement(dateField).sendKeys(date);
        driver.findElement(dateField).sendKeys(Keys.ENTER);
    }

    public void selectRentPeriod(String period) {
        driver.findElement(rentPeriodField).click();
        driver.findElement(By.xpath(String.format(RENT_PERIOD_OPTION, period))).click();
    }

    public void selectColor(String colorId) {
        driver.findElement(By.id(String.format(COLOR_CHECKBOX_ID, colorId))).click();
    }

    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrder() {
        driver.findElement(orderButton).click();
    }

    public void confirmOrder() {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        driver.findElement(confirmYesButton).click();
    }

    public boolean isOrderSuccess() {
        return new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(successHeader))
                .isDisplayed();
    }

    // Шаг: заполнить форму «Про аренду», нажать «Заказать» и подтвердить
    public void fillRentFormAndOrder(String date, String period, String colorId, String comment) {
        waitForLoad();
        setDate(date);
        selectRentPeriod(period);
        selectColor(colorId);
        setComment(comment);
        clickOrder();
        confirmOrder();
    }
}
