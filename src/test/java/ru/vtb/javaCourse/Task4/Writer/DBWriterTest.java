package ru.vtb.javaCourse.Task4.Writer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.vtb.javaCourse.Task4.Repository.LoginRepo;
import ru.vtb.javaCourse.Task4.Service.Task4Service;

@SpringBootTest
@DirtiesContext
class DBWriterTest {

    @Autowired
    DBWriter dbWriter;
    @Autowired
    LoginRepo loginRepo;

    @Test
    //Проверяем переключение, проводим чтение с новой базы, проверяем что нет исключения
    public void CheckWriteDB(){
        dbWriter.init(Task4Service.buildMap("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres"));
        loginRepo.findById(1);
    }

}