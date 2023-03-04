package com.theinfinity.srpingbootinstagram;

import com.theinfinity.srpingbootinstagram.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SrpingbootinstagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrpingbootinstagramApplication.class, args);
	}

}
