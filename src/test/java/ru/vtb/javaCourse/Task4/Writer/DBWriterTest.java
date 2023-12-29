package ru.vtb.javaCourse.Task4.Writer;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;
import ru.vtb.javaCourse.Task4.Repository.LoginRepo;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
class DBWriterTest {

    @Autowired
    DBWriter dbWriter;
    @Autowired
    LoginRepo loginRepo;

    @Test
    //Проверяем переключение, проводи чтение с новой базы, проверяем что нет исключения
    public void CheckWriteDB(){
        dbWriter.init(DBWriter.buildMap("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres"));
        Login login = loginRepo.findById(1).orElse(null);
    }

}