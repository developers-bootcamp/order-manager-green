package com.sap.ordermanegergreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import "../../../../target/generated-source/annotations/com.sap.ordermanegergreen.mapper.ICustomerMapperImpl"
@SpringBootApplication
//@Import(ICustomerMapperImpl.class)
//@ComponentScan(basePackages = {"../../../../target/generated-source/annotations/com.sap.ordermanegergreen.mapper.IUserMapperImpl"})
public class OrderManegerGreenApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderManegerGreenApplication.class, args);
	}
}
