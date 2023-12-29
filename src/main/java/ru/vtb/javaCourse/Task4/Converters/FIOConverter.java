package ru.vtb.javaCourse.Task4.Converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.LogTransformation.LogTransformation;

import java.util.List;

@Component @Qualifier("FirstConverter") @LogTransformation
public class FIOConverter implements Convertable<Login, Login> {
    @Override
    public List<Login> covert(List<Login> sourceList) {
        for(Login source : sourceList) {
            StringBuilder result = new StringBuilder(512);
            for (String str : source.getUser().getFio().split("\\s")) {
                result.append(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() + " ");
            }
            source.getUser().setFio(result.toString().trim());
        }
        return sourceList;
    }
    @Autowired
    @Qualifier("applicationConverter")
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
