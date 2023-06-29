package com.sap.ordermanegergreen;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class OrderManegerGreenApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderManegerGreenApplication.class, args);
	}
}
