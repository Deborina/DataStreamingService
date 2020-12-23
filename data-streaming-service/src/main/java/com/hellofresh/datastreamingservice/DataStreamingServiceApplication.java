package com.hellofresh.datastreamingservice;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hellofresh"})
public class DataStreamingServiceApplication {

	public static void main(String[] args) {
		   TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(DataStreamingServiceApplication.class, args);
	}
	
}
