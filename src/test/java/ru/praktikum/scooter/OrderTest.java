package ru.praktikum.scooter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.scooter.pageobject.OrderPage;
import ru.praktikum.scooter.pageobject.RentPage;

import static org.junit.Assert.assertTrue;

// Заказ самоката: позитивный сценарий через обе кнопки «Заказать» с разными данными
@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private final boolean isTopButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String station;
    private final String phone;
    private final String date;
    private final String period;
    private final String color;
    private final String comment;

    public OrderTest(boolean isTopButton, String name, String surname, String address, String station,
                     String phone, String date, String period, String color, String comment) {
        this.isTopButton = isTopButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Кнопка вверху: {0}, клиент: {1} {2}")
    public static Object[][] getOrderData() {
        return new Object[][]{
                {true, "Айгерим", "Исмаилова", "Москва, ул. Тверская, 1", "Черкизовская",
                        "+79001234567", "01.12.2026", "сутки", "black", "Позвонить за час"},
                {false, "Иван", "Петров", "Москва, Ленинский пр., 10", "Сокольники",
                        "89007654321", "15.12.2026", "двое суток", "grey", ""},
        };
    }

    @Test
    public void orderScooterSuccess() {
        mainPage.clickOrderButton(isTopButton);

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillCustomerForm(name, surname, address, station, phone);

        RentPage rentPage = new RentPage(driver);
        rentPage.fillRentFormAndOrder(date, period, color, comment);

        assertTrue("Окно об успешном заказе не появилось", rentPage.isOrderSuccess());
    }
}