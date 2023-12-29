package ru.vtb.javaCourse.Task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vtb.javaCourse.Task4.Converters.ApplicationConverter;
import ru.vtb.javaCourse.Task4.Converters.Convertable;
import ru.vtb.javaCourse.Task4.Converters.DateConverter;
import ru.vtb.javaCourse.Task4.Converters.FIOConverter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogAnalizerTest {
    @Autowired
    LogAnalizer analizer;

    @Test
    public void testLogAnalizer(){
        Convertable converter =  analizer.converter;
        Assertions.assertTrue(converter.getRealClass() == FIOConverter.class, converter.getClass().getName());
        converter = converter.getNext();
        Assertions.assertTrue(converter.getRealClass() == ApplicationConverter.class, converter.getClass().getName());
        converter = converter.getNext();
        Assertions.assertTrue(converter.getRealClass() == DateConverter.class, converter.getClass().getName());
    }
}