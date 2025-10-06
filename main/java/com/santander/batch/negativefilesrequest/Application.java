package com.santander.batch.negativefilesrequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

/**
 * The main class of
 * the Spring applications.
 * The annotation EnableCaching,
 * enables Spring's annotation-driven
 * cache management capability.
 *
 * @author Santander Technology
 */
@SpringBootApplication
@EnableCaching
@Slf4j
public class Application implements CommandLineRunner {

	/**
	 * Main method of the application
	 * where the application entry-point is.
	 * Depending on the kind of application,
	 * the context of Spring can be configured
	 * to support not web, reactive web, and
	 * servlet web applications.
	 *
	 * This application is defined as notWeb
	 * web application (WebApplicationType.NONE)
	 *
	 * @param args input arguments
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	/**
	 * Callback to run the Application class
	 *
	 * @param args String[] args
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {
		log.info("Running demo commandLineRunner");
	}
}