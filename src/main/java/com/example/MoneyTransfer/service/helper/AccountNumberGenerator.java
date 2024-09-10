package com.example.MoneyTransfer.service.helper;

import java.time.Year;
import java.util.Random;

public class AccountNumberGenerator {

    /*public static String generateAccountNumber() {
        int currentYear = Year.now().getValue();
        String yearPrefix = String.valueOf(currentYear);

        Random random = new Random();
        StringBuilder remainingDigits = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            remainingDigits.append(random.nextInt(10));
        }

        return yearPrefix + remainingDigits.toString();
    }*/

    public static String generateAccountNumber() {
        return String.format("%d%08d", Year.now().getValue(), new Random().nextInt(100000000));
    }
}