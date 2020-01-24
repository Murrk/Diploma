package ru.netology.diploma.Test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;
import ru.netology.diploma.Data.DataGenerator;
import ru.netology.diploma.Db.DbInteraction;
import ru.netology.diploma.Page.PaymentPage;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPayment {
    private String serviceUrl = "http://localhost:8080";
    private String cardGood = "4444444444444441";
    private String cardBad = "4444444444444442";
    private String cardUnknow = "4444444444444443";
    private String fieldNull = "";
    private String invalidMonth = "00";
    private String oldYear = "19";
    private String invalidYear = "88";
    private String cyrillicName = "Иван Пупкин";
    private String invalidName = "1234567";
    private String invalidCvc = "aaa";
    private int timeoutTenSeconds = 10000;
    private String statusApproved = "APPROVED";
    private String statusDeclined = "DECLINED";
    private DataGenerator.User user;
    PaymentPage paymentPage = new PaymentPage();


    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @AfterAll
    static void clearDB() throws SQLException { DbInteraction.clearDb(); }

    @BeforeEach
    void setUp() {
        user = DataGenerator.getUserInfo();
    }

    //Позитивные
    @Test
    @DisplayName("Позитивный, покупка за наличные, карта активная")
    void shouldBePositiveBuyForCash() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.notificationSuccessfullyVisible();
    }

    @Test
    @DisplayName("Позитивный, покупка в кредит, карта активная")
    void shouldBePositiveBuyInCredit() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
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
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardBad,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.notificationErrorVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, карта не активная, ожидаем отказ")
    void shouldBePositiveBuyInCreditDeclined() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardBad,
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
    void shouldBeNegativeBuyForCasheCardNull() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                fieldNull,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, номер карты не заполнен")
    void shouldBeNegativeBuyInCreditCardNull() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                fieldNull,
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
    void shouldBeNegativeBuyForCasheMonthNull() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                fieldNull,
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, не указан месяц")
    void shouldBeNegativeBuyInCreditMonthNull() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                fieldNull,
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, месяц не существует")
    void shouldBeNegativeBuyForCasheUnknowMonth() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                invalidMonth,
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.invalidExpirationDateVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, месяц не существует")
    void shouldBeNegativeBuyInCreditCardUnknowMonth() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                invalidMonth,
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.invalidExpirationDateVisible();
    }

    //Поле "Год"
    @Test
    @DisplayName("Покупка за наличные, не указан год")
    void shouldBeNegativeBuyForCasheYearNull() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                fieldNull,
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, не указан год")
    void shouldBeNegativeBuyInCreditYearNull() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                fieldNull,
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, год не существует")
    void shouldBeNegativeBuyForCasheUnknowYear() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                invalidYear,
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.invalidExpirationDateVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, год не существует")
    void shouldBeNegativeBuyInCreditUnknowYear() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                invalidYear,
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.invalidExpirationDateVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, год старый")
    void shouldBeNegativeBuyForCasheOldYear() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                oldYear,
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.expiredCardVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, год старый")
    void shouldBeNegativeBuyInCreditOldYear() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                oldYear,
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.expiredCardVisible();
    }

    //Поле "Владелец"
    @Test
    @DisplayName("Покупка за наличные, имя не заполнено")
    void shouldBeNegativeBuyForCasheNameNull() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                fieldNull,
                user.getCvc()
        );
        paymentPage.requiredFieldVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, имя не заполнено")
    void shouldBeNegativeBuyInCreditNameNull() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                fieldNull,
                user.getCvc()
        );
        paymentPage.requiredFieldVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, имя заполнено кириллицей")
    void shouldBeNegativeBuyForCasheNameRus() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                cyrillicName,
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, имя заполнено кириллицей")
    void shouldBeNegativeBuyInCreditNameRus() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                cyrillicName,
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, имя заполнено цифрами")
    void shouldBeNegativeBuyForCasheNameNumbers() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                invalidName,
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, имя заполнено цифрами")
    void shouldBeNegativeBuyInCreditNameNumbers() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                invalidName,
                user.getCvc()
        );
        paymentPage.wrongFormatVisible();
    }

    //Поле "Cvc/cvv"
    @Test
    @DisplayName("Покупка за наличные, код не заполнен")
    void shouldBeNegativeBuyForCasheCvcNull() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                fieldNull
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, код не заполнен")
    void shouldBeNegativeBuyInCreditCvcNull() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                fieldNull
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка за наличные, код заполнен буквами")
    void shouldBeNegativeBuyForCasheCvcChar() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                invalidCvc
        );
        paymentPage.wrongFormatVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, код заполнен буквами")
    void shouldBeNegativeBuyInCreditCvcChar() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                invalidCvc
        );
        paymentPage.wrongFormatVisible();
    }

    //Неизвестная карта
    @Test
    @DisplayName("Покупка за наличные, карта не известная")
    void shouldBeNegativeBuyForCasheCardUnknow() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardUnknow,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        paymentPage.notificationErrorVisible();
    }

    @Test
    @DisplayName("Покупка в кредит, карта не известная")
    void shouldBeNegativeBuyInCreditCardUnknow() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardUnknow,
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
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc());
        sleep(timeoutTenSeconds);
        assertEquals(statusApproved, DbInteraction.paymentStatus());
        DbInteraction.checkPaymentId();
    }

    @Test
    @DisplayName("Позитивный, покупка в кредит, карта активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyInCreditBdApproved() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardGood,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        sleep(timeoutTenSeconds);
        assertEquals(statusApproved, DbInteraction.creditStatus());
        DbInteraction.checkCreditId();
    }

    @Test
    @DisplayName("Покупка за наличные, карта не активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyForCashBdDeclined() {
        open(serviceUrl);
        paymentPage.buyForCash();
        paymentPage.fillingFields(
                cardBad,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        sleep(timeoutTenSeconds);
        assertEquals(statusDeclined, DbInteraction.paymentStatus());
        DbInteraction.checkPaymentId();
    }

    @Test
    @DisplayName("Покупка в кредит, карта не активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyInCreditBdDeclined() {
        open(serviceUrl);
        paymentPage.buyInCredit();
        paymentPage.fillingFields(
                cardBad,
                user.getCardMonth(),
                user.getCardYear(),
                user.getFullName(),
                user.getCvc()
        );
        sleep(timeoutTenSeconds);
        assertEquals(statusDeclined, DbInteraction.creditStatus());
        DbInteraction.checkCreditId();
    }
}
