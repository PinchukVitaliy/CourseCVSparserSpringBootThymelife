package pink.coursework.csvparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//При настройке проекта Spring boot используйте свой класс приложения
//(тот, который содержит аннотацию @SpringBootApplication) в базовом пакете.
@SpringBootApplication
public class CsvparserApplication {
	public static void main(String[] args) {
		SpringApplication.run(CsvparserApplication.class, args);
	}
}
