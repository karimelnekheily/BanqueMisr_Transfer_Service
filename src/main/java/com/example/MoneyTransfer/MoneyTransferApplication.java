package com.example.MoneyTransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MoneyTransferApplication {
	public String PORT=System.getenv("PORT");

	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferApplication.class, args);
	}

}
