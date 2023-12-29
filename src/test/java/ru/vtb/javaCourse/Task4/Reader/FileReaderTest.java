package ru.vtb.javaCourse.Task4.Reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vtb.javaCourse.Task4.Entity.Login;

import java.nio.charset.Charset;
import java.util.List;

class FileReaderTest {

    @Test
    public void loadFile(){
        FileReader reader = new FileReader();
        reader.setCharset(Charset.forName("Windows-1251"));
        List<Login> strs = reader.readAll(getClass().getClassLoader().getResource("logs/example.log").getFile());
        Assertions.assertEquals(5, strs.size(), "Неверное количество строк");
        //login1 Ф1 И1 О1 2023-12-20T02:30:37.158402200Z web
        Assertions.assertEquals("login1", strs.get(0).getUser().getUserName(), "Неверное значение поля Логин");
        Assertions.assertEquals("Ф1 И1 О1", strs.get(0).getUser().getFio(), "Неверное значение поля Фамилия");
        Assertions.assertEquals("2023-12-20T02:30:37.158", strs.get(0).getAccess_data().toString(), "Неверное значение поля Время события");
        Assertions.assertEquals("web", strs.get(0).getApplication(), "Неверное значение поля Приложение");
        Assertions.assertNull(strs.get(4).getAccess_data(), "Поле дата доступа должно быть пустым");
    }
}