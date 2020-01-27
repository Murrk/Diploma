package ru.netology.diploma.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;
import ru.netology.diploma.data.DataGenerator;
import ru.netology.diploma.db.DbInteraction;
import ru.netology.diploma.page.ChoicePaymentPage;
import ru.netology.diploma.page.PaymentPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPayment {
    private String serviceUrl = "http://localhost:8080";
    private int timeoutTenSeconds = 10000;
    private DataGenerator.User user;
    PaymentPage paymentPage = new PaymentPage();
    ChoicePaymentPage choicePaymentPage = new ChoicePaymentPage();

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @AfterAll
    static void clearDB() { DbInteraction.clearDb(); }

    @BeforeEach
    void setUp() {
        user = DataGenerator.getUserInfo();
    }

    //Позитивные
    @Test
    @DisplayName("Позитивный, покупка за наличные, карта активная")
    void shouldBePositiveBuyForCash() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.notificationSuccessfullyVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, карта не активная, ожидаем отказ")
    void shouldBePositiveBuyForCashDeclined() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberBad(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.notificationErrorVisible();
    }

    //Негативные
    //Поле "Номер карты"
    @Test
    @DisplayName("Покупка за наличные, номер карты не заполнен")
    void shouldBeNegativeBuyForCashCardNull() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getFieldNull(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    //Поле "Месяц"
    @Test
    @DisplayName("Покупка за наличные, не указан месяц")
    void shouldBeNegativeBuyForCashMonthNull() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                DataGenerator.getFieldNull(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, месяц не существует")
    void shouldBeNegativeBuyForCashUnknownMonth() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                DataGenerator.getInvalidMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.invalidExpirationDateVisible();
    }

    //Поле "Год"
    @Test
    @DisplayName("Покупка за наличные, не указан год")
    void shouldBeNegativeBuyForCashYearNull() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                DataGenerator.getFieldNull(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, год не существует")
    void shouldBeNegativeBuyForCashUnknownYear() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                DataGenerator.getInvalidYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.invalidExpirationDateVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, год старый")
    void shouldBeNegativeBuyForCashOldYear() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                DataGenerator.getOldYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.expiredCardVisible();
    }

    //Поле "Владелец"
    @Test
    @DisplayName("Покупка за наличные, имя не заполнено")
    void shouldBeNegativeBuyForCashNameNull() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                user.getCardYear(),
                DataGenerator.getFieldNull(),
                user.getCvc()
        );
        paymentPage.requiredFieldVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, имя заполнено кириллицей")
    void shouldBeNegativeBuyForCashNameRus() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                user.getCardYear(),
                DataGenerator.getCyrillicName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, имя заполнено цифрами")
    void shouldBeNegativeBuyForCashNameNumbers() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                user.getCardYear(),
                DataGenerator.getInvalidName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    //Поле "Cvc/cvv"
    @Test
    @DisplayName("Покупка за наличные, код не заполнен")
    void shouldBeNegativeBuyForCashCvcNull() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                DataGenerator.getFieldNull()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, код заполнен буквами")
    void shouldBeNegativeBuyForCashCvcChar() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                DataGenerator.getInvalidCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    //Неизвестная карта
    @Test
    @DisplayName("Покупка за наличные, карта не известная")
    void shouldBeNegativeBuyForCashCardUnknown() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberForUnknown(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.notificationErrorVisible();
    }

    //Проверка взаимодействия с БД"
    @Test
    @DisplayName("Позитивный, покупка за наличные, карта активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyForCashBdApproved() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc());
        sleep(timeoutTenSeconds);
        assertEquals(DataGenerator.getStatusApproved(), DbInteraction.paymentStatus());
        DbInteraction.checkPaymentId();
    }

    @Test
    @DisplayName("Покупка за наличные, карта не активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyForCashBdDeclined() {
        open(serviceUrl);
        choicePaymentPage.buyForCash();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberBad(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        sleep(timeoutTenSeconds);
        assertEquals(DataGenerator.getStatusDeclined(), DbInteraction.paymentStatus());
        DbInteraction.checkPaymentId();
    }
}
