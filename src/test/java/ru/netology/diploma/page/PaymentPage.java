package ru.netology.diploma.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement fieldCardNumber = $$(".input__control").get(0);
    private SelenideElement fieldCardMonth = $$(".input__control").get(1);
    private SelenideElement fieldCardYear = $$(".input__control").get(2);
    private SelenideElement fieldFullName = $$(".input__control").get(3);
    private SelenideElement fieldCvc = $$(".input__control").get(4);
    private SelenideElement continueButton = $$(".button__content").find(exactText("Продолжить"));
    private SelenideElement notificationSuccessfully = $$(".notification__title").find(exactText("Успешно"));
    private SelenideElement notificationError = $$(".notification__title").find(exactText("Ошибка"));
    private SelenideElement wrongFormat = $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement invalidExpirationDate = $$(".input__sub").find(exactText("Неверно указан срок действия карты"));
    private SelenideElement expiredCard = $$(".input__sub").find(exactText("Истёк срок действия карты"));
    private SelenideElement requiredField = $$(".input__sub").find(exactText("Поле обязательно для заполнения"));
    private int timeoutTenSeconds = 10000;

    public void fillingFields(String card, String cardMonth, String cardYear, String name, String cvc){
        fieldCardNumber.setValue(card);
        fieldCardMonth.setValue(cardMonth);
        fieldCardYear.setValue(cardYear);
        fieldFullName.setValue(name);
        fieldCvc.setValue(cvc);
        continueButton.click();
    }

    public void notificationSuccessfullyVisible() {
        notificationSuccessfully.waitUntil(visible, timeoutTenSeconds);
    }

    public void notificationErrorVisible() {
        notificationError.waitUntil(visible, timeoutTenSeconds);
    }

    public void wrongFormatVisible() {
        wrongFormat.shouldBe(visible);
    }

    public void invalidExpirationDateVisible() {
        invalidExpirationDate.shouldBe(visible);
    }

    public void expiredCardVisible() {
        expiredCard.shouldBe(visible);
    }

    public void requiredFieldVisible() {
        requiredField.shouldBe(visible);
    }
}
