package com.example.MoneyTransfer.util;

import java.time.Year;
import java.util.Random;
import java.util.UUID;

public class AccountNumberGenerator {

    public static String generateAccountNumber() {

         String currentYear = String.valueOf(Year.now().getValue());

        Random random = new Random();
        long randomNumber = 10000000L + (long)(random.nextDouble() * 89999999L);

        // Format the account number as YYYY XXXX XXXX
        return String.format("%s%04d%04d",
                currentYear,
                (randomNumber / 10000) % 10000,
                randomNumber % 10000);

    }
}
