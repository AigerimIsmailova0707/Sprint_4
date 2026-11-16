package ru.praktikum.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Главная страница Яндекс Самоката
public class MainPage {

    public static final String URL = "https://qa-scooter.praktikum-services.ru/";

    private final WebDriver driver;

    // Кнопка «да все привыкли» в плашке про куки
    private final By cookieButton = By.id("rcc-confirm-button");

    // Кнопка «Заказать» вверху страницы (в шапке)
    private final By orderButtonTop = By.xpath(".//div[contains(@class,'Header_Nav')]/button[text()='Заказать']");

    // Кнопка «Заказать» внизу страницы
    private final By orderButtonBottom = By.xpath(".//div[contains(@class,'Home_FinishButton')]/button[text()='Заказать']");

    // Вопрос в разделе «Вопросы о важном» (стрелочка), index от 0 до 7
    private static final String FAQ_QUESTION_ID = "accordion__heading-%d";

    // Ответ на вопрос в разделе «Вопросы о важном», index от 0 до 7
    private static final String FAQ_ANSWER_ID = "accordion__panel-%d";

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(URL);
    }

    public void acceptCookies() {
        // ждём плашку до 3 секунд; если её нет — просто идём дальше
        try {
            new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(cookieButton)).click();
        } catch (org.openqa.selenium.TimeoutException e) {
            // плашки нет
        }
    }

    public void clickOrderButton(boolean isTopButton) {
        By button = isTopButton ? orderButtonTop : orderButtonBottom;
        WebElement element = driver.findElement(button);
        scrollTo(element);
        element.click();
    }

    public void clickFaqQuestion(int index) {
        WebElement question = driver.findElement(By.id(String.format(FAQ_QUESTION_ID, index)));
        scrollTo(question);
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(question));
        question.click();
    }

    public String getFaqAnswerText(int index) {
        By answer = By.id(String.format(FAQ_ANSWER_ID, index));
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(answer));
        return driver.findElement(answer).getText();
    }

    private void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }
}
