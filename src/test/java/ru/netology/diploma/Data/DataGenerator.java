package ru.netology.diploma.Data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

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
        return new User(
                Integer.toString(faker.number().numberBetween(10, 12)),
                Integer.toString(faker.number().numberBetween(21, 25)),
                faker.name().fullName(),
                faker.numerify("###")
        );
    }

}
