package ru.vtb.javaCourse.Task4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.vtb.javaCourse.Task4.Service.Task4Service;
import ru.vtb.javaCourse.Task4.Writer.DBWriter;

@SpringBootApplication(scanBasePackages = "ru.vtb.javaCourse.Task4")
public class Task4Application {

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(Task4Application.class, args);
		Task4Service service = appContext.getBean(Task4Service.class);
		DBWriter dbWriter = appContext.getBean(DBWriter.class);
		dbWriter.init(Task4Service.buildMap("jdbc:postgresql://localhost:5432/postgres1","postgres","postgres"));
	}

}
