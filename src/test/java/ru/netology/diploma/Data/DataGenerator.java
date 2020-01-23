package ru.netology.diploma.Data;

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
        return new User(
                getMonth("MM"),
                Integer.toString(faker.number().numberBetween(21, 25)),
                fullName,
                faker.numerify("###")
        );
    }

    public static String getMonth(String formatDate) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, 2);
        SimpleDateFormat format = new SimpleDateFormat(formatDate);
        String month = format.format(c.getTime());
        return month;
    }
}
