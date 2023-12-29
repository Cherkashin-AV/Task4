package ru.vtb.javaCourse.Task4;

import org.hibernate.engine.jdbc.spi.JdbcWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.vtb.javaCourse.Task4.Writer.DBWriter;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "ru.vtb.javaCourse.Task4")
public class Task4Application {

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(Task4Application.class, args);
		DBWriter dbWriter = appContext.getBean(DBWriter.class);
		dbWriter.init(DBWriter.buildMap("jdbc:postgresql://localhost:5432/postgres1","postgres","postgres"));
//		Arrays.stream(appContext.getBeanDefinitionNames()).sorted().forEach(System.out::println);
//		LogAnalizer analizer = new LogAnalizer();
//		analizer.alalize(null);
	}

}
