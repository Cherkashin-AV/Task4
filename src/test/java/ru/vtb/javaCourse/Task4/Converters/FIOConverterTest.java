package ru.vtb.javaCourse.Task4.Converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FIOConverterTest {
    @Test
    public void convert(){
        FIOConverter fioConverter= new FIOConverter();
        Login login = new Login(null, null, new User(null, null, "аа бб вв"), "web");
        fioConverter.covert(List.of(login));
        Assertions.assertEquals("Аа Бб Вв", login.getUser().getFio(), "ФИО не нормализовано");
    }



}