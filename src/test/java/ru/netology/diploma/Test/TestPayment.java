package ru.netology.diploma.Test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;
import ru.netology.diploma.Data.DataGenerator;
import ru.netology.diploma.Db.DbInteraction;
import ru.netology.diploma.Page.PaymentPage;
import java.sql.SQLException;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPayment {
    private String serviceUrl = "http://localhost:8080";
    private String cardGood = "4444444444444441";
    private String cardBad = "4444444444444442";
    private String cardUnknow = "4444444444444443";
    private SelenideElement notificationSuccessfully = $$(".notification__title").find(exactText("Успешно"));
    private SelenideElement notificationError = $$(".notification__title").find(exactText("Ошибка"));
    private SelenideElement wrongFormat = $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement invalidExpirationDate = $$(".input__sub").find(exactText("Неверно указан срок действия карты"));
    private SelenideElement expiredCard = $$(".input__sub").find(exactText("Истёк срок действия карты"));
    private SelenideElement requiredField = $$(".input__sub").find(exactText("Поле обязательно для заполнения"));
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
    @DisplayName("Позитвный, покупка за наличные, карта активная")
    void shouldBePositiveBuyForCash() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        notificationSuccessfully.waitUntil(visible, 10000);
    }

    @Test
    @DisplayName("Позитвный, покупка в кредит, карта активная")
    void shouldBePositiveBuyInCredit() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        notificationSuccessfully.waitUntil(visible, 10000);
    }

    @Test
    @DisplayName("Покупка за наличные, карта не активная, ожидаем отказ")
    void shouldBePositiveBuyForCashDeclined() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardBad, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        notificationError.waitUntil(visible, 10000);
    }

    @Test
    @DisplayName("Покупка в кредит, карта не активная, ожидаем отказ")
    void shouldBePositiveBuyInCreditDeclined() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardBad, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        notificationError.waitUntil(visible, 10000);
    }

    //Негативные
    //Поле номер карты
    @Test
    @DisplayName("Покупка за наличные, номер карты не заполнен")
    void shouldBeNegativeBuyForCasheCardNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash("", user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, номер карты не заполнен")
    void shouldBeNegativeBuyInCreditCardNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit("", user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    //Неизвестная карта
    @Test
    @DisplayName("Покупка за наличные, карта не известная")
    void shouldBeNegativeBuyForCasheCardUnknow() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardUnknow, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        notificationError.waitUntil(visible, 10000);
    }

    @Test
    @DisplayName("Покупка в кредит, карта не известная")
    void shouldBeNegativeBuyInCreditCardUnknow() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardUnknow, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        notificationError.waitUntil(visible, 10000);
    }

    //Поле месяц
    @Test
    @DisplayName("Покупка за наличные, не указан месяц")
    void shouldBeNegativeBuyForCasheMonthNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, "", user.getCardYear(), user.getFullName(), user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, не указан месяц")
    void shouldBeNegativeBuyInCreditMonthNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, "", user.getCardYear(), user.getFullName(), user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка за наличные, месяц не существует")
    void shouldBeNegativeBuyForCasheUnknowMonth() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, "00", user.getCardYear(), user.getFullName(), user.getCvc());
        invalidExpirationDate.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, месяц не существует")
    void shouldBeNegativeBuyInCreditCardUnknowMonth() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, "00", user.getCardYear(), user.getFullName(), user.getCvc());
        invalidExpirationDate.shouldBe(visible);
    }

    //Поле год
    @Test
    @DisplayName("Покупка за наличные, год старый")
    void shouldBeNegativeBuyForCasheOldYear() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), "19", user.getFullName(), user.getCvc());
        expiredCard.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, год старый")
    void shouldBeNegativeBuyInCreditOldYear() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), "19", user.getFullName(), user.getCvc());
        expiredCard.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка за наличные, не указан год")
    void shouldBeNegativeBuyForCasheYearNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), "", user.getFullName(), user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, не указан год")
    void shouldBeNegativeBuyInCreditYearNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), "", user.getFullName(), user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка за наличные, год не существует")
    void shouldBeNegativeBuyForCasheUnknowYear() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), "88", user.getFullName(), user.getCvc());
        invalidExpirationDate.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, год не существует")
    void shouldBeNegativeBuyInCreditUnknowYear() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), "88", user.getFullName(), user.getCvc());
        invalidExpirationDate.shouldBe(visible);
    }

    //Поле Владелец
    @Test
    @DisplayName("Покупка за наличные, имя не заполнено")
    void shouldBeNegativeBuyForCasheNameNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), user.getCardYear(), "", user.getCvc());
        requiredField.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, имя не заполнено")
    void shouldBeNegativeBuyInCreditNameNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), user.getCardYear(), "", user.getCvc());
        requiredField.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка за наличные, имя заполнено кириллицей")
    void shouldBeNegativeBuyForCasheNameRus() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), user.getCardYear(), "Иван Пупкин", user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, имя заполнено кириллицей")
    void shouldBeNegativeBuyInCreditNameRus() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), user.getCardYear(), "Иван Пупкин", user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка за наличные, имя заполнено цифрами")
    void shouldBeNegativeBuyForCasheNameNumbers() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), user.getCardYear(), "123456789", user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, имя заполнено цифрами")
    void shouldBeNegativeBuyInCreditNameNumbers() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), user.getCardYear(), "123456789", user.getCvc());
        wrongFormat.shouldBe(visible);
    }

    //Поле cvc/cvv
    @Test
    @DisplayName("Покупка за наличные, код не заполнен")
    void shouldBeNegativeBuyForCasheCvcNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), user.getCardYear(), user.getFullName(),"");
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, код не заполнен")
    void shouldBeNegativeBuyInCreditCvcNull() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), user.getCardYear(), user.getFullName(), "");
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка за наличные, код заполнен буквами")
    void shouldBeNegativeBuyForCasheCvcChar() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), user.getCardYear(), user.getFullName(),"ааа");
        wrongFormat.shouldBe(visible);
    }

    @Test
    @DisplayName("Покупка в кредит, код заполнен буквами")
    void shouldBeNegativeBuyInCreditCvcChar() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), user.getCardYear(), user.getFullName(), "ааа");
        wrongFormat.shouldBe(visible);
    }

    //Проверка взаимодействия с БД"

    @Test
    @DisplayName("Позитвный, покупка за наличные, карта активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyForCashBdApproved() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardGood, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        sleep(10000);
        assertEquals("APPROVED", DbInteraction.paymentStatus());
    }

    @Test
    @DisplayName("Позитвный, покупка в кредит, карта активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyInCreditBdApproved() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardGood, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        sleep(10000);
        assertEquals("APPROVED", DbInteraction.creditStatus());
    }

    @Test
    @DisplayName("Покупка за наличные, карта не активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyForCashBdDeclined() throws SQLException {
        open(serviceUrl);
        paymentPage.buyForCash(cardBad, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        sleep(10000);
        assertEquals("DECLINED", DbInteraction.paymentStatus());
    }

    @Test
    @DisplayName("Покупка в кредит, карта не активная, проверка взаимодействия с БД")
    void shouldBePositiveBuyInCreditBdDeclined() throws SQLException {
        open(serviceUrl);
        paymentPage.buyInCredit(cardBad, user.getCardMonth(), user.getCardYear(), user.getFullName(), user.getCvc());
        sleep(10000);
        assertEquals("DECLINED", DbInteraction.creditStatus());
    }
}
