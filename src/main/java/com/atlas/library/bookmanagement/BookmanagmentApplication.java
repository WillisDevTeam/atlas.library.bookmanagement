package com.atlas.library.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookmanagmentApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableBootstrapContext =
				SpringApplication.run(BookmanagmentApplication.class, args);

		//BookMgmtRepository bookMgmtRepository = configurableBootstrapContext.getBean(BookMgmtRepository.class);

	}

}
