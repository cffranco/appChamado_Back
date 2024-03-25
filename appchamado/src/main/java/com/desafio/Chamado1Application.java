package com.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.desafio.configuration.RsaKeyConfigProperties;


@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class Chamado1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chamado1Application.class, args);
	}

}
