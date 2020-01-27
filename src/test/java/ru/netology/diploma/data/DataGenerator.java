package ru.netology.diploma.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {}

    @Value
    @Data
    @RequiredArgsConstructor
    public static class User {
        private final String cardMonth;
        private final String cardYear;
        private final String fullName;
        private final String cvc;
    }

    public static User getUserInfo(){
        Faker faker = new Faker(new Locale("En"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String fullName = (firstName + " " + lastName);
        int fieldMonth = faker.number().numberBetween(1, 12);
        String fieldMonthFormat = String.format("%02d", fieldMonth);
        return new User(
                fieldMonthFormat,
                Integer.toString(faker.number().numberBetween(21, 25)),
                fullName,
                faker.numerify("###")
        );
    }

    public static String getCardNumberGood() {
        return "4444444444444441";
    }

    public static String getCardNumberBad() {
        return "4444444444444442";
    }

    public static String getCardNumberForUnknown() {
        return "4444444444444443";
    }

    public static String getStatusApproved() {
        return "APPROVED";
    }

    public static String getStatusDeclined() { return "DECLINED"; }

    public static String getFieldNull() { return ""; }

    public static String getInvalidMonth() { return "00"; }

    public static String getOldYear() { return getDate("-1", "YY"); }

    public static String getInvalidYear() { return getDate("-33", "YY"); }

    public static String getCyrillicName() {
        Faker faker = new Faker(new Locale("Ru"));
        return faker.name().fullName();
    }

    public static String getInvalidName() {
        Faker faker = new Faker();
        return faker.numerify("######");
    }

    public static String getInvalidCvc() {
        Faker faker = new Faker();
        return faker.letterify("###");
    }

    private static String getDate(String countDate, String formatDate) {
        int countDateInt = Integer.parseInt(countDate);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.YEAR, countDateInt);
        SimpleDateFormat format = new SimpleDateFormat(formatDate);
        String date = format.format(c.getTime());
        return date;
    }
}
