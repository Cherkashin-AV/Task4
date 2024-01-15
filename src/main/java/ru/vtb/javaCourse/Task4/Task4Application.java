package ru.vtb.javaCourse.Task4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.vtb.javaCourse.Task4.Service.Task4Service;
import ru.vtb.javaCourse.Task4.Writer.DBWriter;

import java.nio.charset.Charset;

@SpringBootApplication(scanBasePackages = "ru.vtb.javaCourse.Task4")
public class Task4Application {

	private static final String jdbcurl = "jdbc:postgresql://localhost:5432/postgres1";
	private static final String username = "postgres";
	private static final String password = "postgres";
	private static final Charset charset = Charset.forName("Windows-1251");


	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(Task4Application.class, args);
		LogAnalizer logAnalizer = appContext.getBean(LogAnalizer.class);
		logAnalizer.setDBConnection(jdbcurl, username, password);
		logAnalizer.analize("./src/test/resources/logs", charset);
	}

}
