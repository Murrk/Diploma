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

public class TestCredit {
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
    @DisplayName("Позитивный, покупка в кредит, карта активная")
    void shouldBePositiveBuyInCredit() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, карта не активная, ожидаем отказ")
    void shouldBePositiveBuyInCreditDeclined() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, номер карты не заполнен")
    void shouldBeNegativeBuyInCreditCardNull() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, не указан месяц")
    void shouldBeNegativeBuyInCreditMonthNull() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, месяц не существует")
    void shouldBeNegativeBuyInCreditCardUnknownMonth() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, не указан год")
    void shouldBeNegativeBuyInCreditYearNull() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, год не существует")
    void shouldBeNegativeBuyInCreditUnknownYear() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, год старый")
    void shouldBeNegativeBuyInCreditOldYear() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, имя не заполнено")
    void shouldBeNegativeBuyInCreditNameNull() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, имя заполнено кириллицей")
    void shouldBeNegativeBuyInCreditNameRus() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, имя заполнено цифрами")
    void shouldBeNegativeBuyInCreditNameNumbers() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, код не заполнен")
    void shouldBeNegativeBuyInCreditCvcNull() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, код заполнен буквами")
    void shouldBeNegativeBuyInCreditCvcChar() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Покупка в кредит, карта не известная")
    void shouldBeNegativeBuyInCreditCardUnknown() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
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
    @DisplayName("Позитивный, покупка в кредит, карта активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyInCreditBdApproved() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberGood(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        sleep(timeoutTenSeconds);
        assertEquals(DataGenerator.getStatusApproved(), DbInteraction.creditStatus());
        DbInteraction.checkCreditId();
    }

    @Test
    @DisplayName("Покупка в кредит, карта не активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyInCreditBdDeclined() {
        open(serviceUrl);
        choicePaymentPage.buyInCredit();
        paymentPage.fillingFields(
                DataGenerator.getCardNumberBad(),
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        sleep(timeoutTenSeconds);
        assertEquals(DataGenerator.getStatusDeclined(), DbInteraction.creditStatus());
        DbInteraction.checkCreditId();
    }
}

