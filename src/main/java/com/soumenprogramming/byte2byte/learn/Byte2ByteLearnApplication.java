package com.soumenprogramming.byte2byte.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan
public class Byte2ByteLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(Byte2ByteLearnApplication.class, args);
	}

}
