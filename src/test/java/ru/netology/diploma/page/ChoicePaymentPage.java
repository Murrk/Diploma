package ru.netology.diploma.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class ChoicePaymentPage {
    private SelenideElement buyButton = $$(".button__content").find(exactText("Купить"));
    private SelenideElement buyInCreditButton = $$(".button__content").find(exactText("Купить в кредит"));
    private SelenideElement buyForCash = $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте"));
    private SelenideElement buyInCredit = $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты"));

    public PaymentPage buyForCash() {
        buyButton.click();
        buyForCash.shouldBe(visible);
        return new PaymentPage();
    }

    public PaymentPage buyInCredit() {
        buyInCreditButton.click();
        buyInCredit.shouldBe(visible);
        return new PaymentPage();
    }
}
