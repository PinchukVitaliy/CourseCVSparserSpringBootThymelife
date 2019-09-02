package pink.coursework.csvparser;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Это расширение WebApplicationInitializer , которое запускает SpringApplication из традиционного архива WAR,
 * развернутого в веб-контейнере. Этот класс связывает бины Servlet , Filter и ServletContextInitializer из
 * контекста приложения с сервером.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CsvparserApplication.class);
	}

}
