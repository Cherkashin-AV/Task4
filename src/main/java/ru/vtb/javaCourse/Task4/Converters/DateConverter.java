package ru.vtb.javaCourse.Task4.Converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.LogTransformation.LogTransformation;

import java.util.Iterator;
import java.util.List;


@Component
@LogTransformation(showDetail = true)
public class DateConverter implements Convertable<Login, Login> {
    static final Logger log =
            LoggerFactory.getLogger(DateConverter.class);
    public static final String removeRecordMessage = "Пустая дата!";

    @Override
    public List<Login> covert(List<Login> sourceList) {
        Iterator<Login> iterator = sourceList.iterator();
        Login source;
        while(iterator.hasNext()){
            source = iterator.next();
            if (source.getAccess_data() == null) {
                log.warn(removeRecordMessage);
                iterator.remove();
            }
        }
        return sourceList;
    }


    private Convertable convertable;

    @Override
    public Convertable getNext() {
        return convertable;
    }

    @Override
    public Class getRealClass() {
        return this.getClass();
    }

}
