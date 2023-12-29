package ru.vtb.javaCourse.Task4.Converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateConverterTest {
    @Test
    public void convert(){
        DateConverter dateConverter = new DateConverter();
        Login login = new Login(null, null, new User(null, null, "аа бб вв"), "web");
        List<Login> result = dateConverter.covert(new ArrayList<>(Arrays.asList(login)));
        Assertions.assertEquals(0, result.size());
        login.setAccess_data(LocalDateTime.now());
        result = dateConverter.covert(List.of(login));
        Assertions.assertEquals(login, result.get(0));
    }
}