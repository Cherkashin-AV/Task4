package ru.vtb.javaCourse.Task4_IntegratedTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ru.vtb.javaCourse.Task4.Converters.DateConverter;
import ru.vtb.javaCourse.Task4.LogAnalizer;
import ru.vtb.javaCourse.Task4.Service.Task4Service;
import ru.vtb.javaCourse.Task4.Task4Application;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest(classes = {Task4Application.class})
public class IntegratedTest {
    @Autowired
    LogAnalizer logAnalizer;
    @Autowired
    ApplicationContext appContext;

    private final String jdbcurl = "jdbc:postgresql://localhost:5432/postgres1";
    private final String username = "postgres";
    private final String password = "postgres";
    private final Charset charset = Charset.forName("Windows-1251");


    @Autowired
    Task4Service service;

    @Test
    public void fullTest() throws SQLException  {
        //Меняем подключение на тестовую базу
        logAnalizer.setDBConnection(jdbcurl, username, password);

        //Количество записей в БД
        long loginDBCountBefore = service.getLoginCount();
        //Количество записей для обработки
        int recordCount = 0;
        try {
            List<String> records = Files.readAllLines(Paths.get("./src/test/resources/logs/example.log"), charset);
            recordCount = records.size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Количество записай в логе приложения до запуска
        int logCountBefore = 0;
        Path logsFilePath = Paths.get("./log/application/tests.log");
        try {
            logCountBefore = Files.readAllLines(logsFilePath).size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Запускаем тесты
        logAnalizer.analize("./src/test/resources/logs", charset);

        //Считываем состояние после выполнения
        //Общее количество записей в БД
        long loginDBCountAfter = service.getLoginCount();
        Assertions.assertTrue(loginDBCountAfter>loginDBCountBefore, "Не вставлены записи в БД");
        //Количество записей с пустой датой, они в базу не записаны. Будем искать в логе.
        long loginWithError = recordCount - (loginDBCountAfter - loginDBCountBefore);
        //Количество пропущенных записей по данным лога
        int errRecords = 0;
        try {
            List<String> logStrings = Files.readAllLines(logsFilePath);
            for (int i = 0; i < logStrings.size(); i++) {
                if (i>=logCountBefore) {
                    if (logStrings.get(i).endsWith(DateConverter.removeRecordMessage)) {
                        errRecords++;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(recordCount, loginDBCountAfter-loginDBCountBefore + errRecords, "Неверное количество записей. В БД: " + (loginDBCountAfter-loginDBCountBefore) + ", пропущено с пустой датой: " + errRecords );
        Assertions.assertEquals(loginWithError, errRecords, "Неверное количество записей с пустой датой");
    }
}
