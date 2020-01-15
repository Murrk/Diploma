package ru.netology.diploma.Page;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement buyButton = $$(".button__content").find(exactText("Купить"));
    private SelenideElement buyInCreditButton = $$(".button__content").find(exactText("Купить в кредит"));
    private SelenideElement buyForCash = $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте"));
    private SelenideElement buyInCredit = $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты"));
    private SelenideElement fieldCardNumber = $$(".input__control").get(0);
    private SelenideElement fieldCardMonth = $$(".input__control").get(1);
    private SelenideElement fieldCardYear = $$(".input__control").get(2);
    private SelenideElement fieldFullName = $$(".input__control").get(3);
    private SelenideElement fieldCvc = $$(".input__control").get(4);
    private SelenideElement continueButton = $$(".button__content").find(exactText("Продолжить"));

    public void buyForCash (String card, String cardMonth, String cardYear, String name, String cvc) {
        buyButton.click();
        buyForCash.shouldBe(visible);
        fieldCardNumber.setValue(card);
        fieldCardMonth.setValue(cardMonth);
        fieldCardYear.setValue(cardYear);
        fieldFullName.setValue(name);
        fieldCvc.setValue(cvc);
        continueButton.click();
    }

    public void buyInCredit (String card, String cardMonth, String cardYear, String name, String cvc) {
        buyInCreditButton.click();
        buyInCredit.shouldBe(visible);
        fieldCardNumber.setValue(card);
        fieldCardMonth.setValue(cardMonth);
        fieldCardYear.setValue(cardYear);
        fieldFullName.setValue(name);
        fieldCvc.setValue(cvc);
        continueButton.click();
    }


}
