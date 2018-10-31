package com.pig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author arthas
 */

@SpringBootApplication
@ComponentScan("com.pig")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
