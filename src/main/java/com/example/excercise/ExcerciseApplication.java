package com.example.excercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableTransactionManagement
@RestController
public class ExcerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcerciseApplication.class, args);
	}

}
