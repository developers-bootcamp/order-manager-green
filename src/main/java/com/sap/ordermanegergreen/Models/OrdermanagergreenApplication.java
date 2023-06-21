package com.sap.ordermanegergreen.Models;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@EntityScan("myspring.model")
@SpringBootApplication
public class OrdermanagergreenApplication {

	public static void main(String[] args) {
		Address a=new Address("45667","hjkk","dff@vvb");
		a.toString();

		SpringApplication.run(OrdermanagergreenApplication.class, args);
//		Address a=new Address("45667","hjkk","dff@vvb");
//		a.toString();

	}
}
