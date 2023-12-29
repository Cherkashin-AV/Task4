package ru.vtb.javaCourse.Task4.Converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationConverterTest {
    @Test
    public void convert(){
        ApplicationConverter applicationConverter = new ApplicationConverter();
        Login login = new Login(null, null, new User(null, null, "аа бб вв"), "web");
        List<Login> res = applicationConverter.covert(List.of(login));
        Assertions.assertEquals("web", res.get(0).getApplication(), "Конвертер не должен преобразовывать поле Application для значений web и mobile");
        login.setApplication("mobile");
        applicationConverter.covert(List.of(login));
        Assertions.assertEquals("mobile", res.get(0).getApplication(), "Конвертер не должен преобразовывать поле Application для значений web и mobile");
        login.setApplication("other");
        applicationConverter.covert(List.of(login));
        Assertions.assertEquals("other:other", res.get(0).getApplication(), "Значение поле Application должно быть в формате others:<Application_name>");
    }
}