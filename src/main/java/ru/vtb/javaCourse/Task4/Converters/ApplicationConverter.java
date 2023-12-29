package ru.vtb.javaCourse.Task4.Converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Entity.Login;

import java.util.List;
import java.util.Locale;

@Component
public class ApplicationConverter implements Convertable<Login,Login> {
    @Override
    public List<Login> covert(List<Login> sourceList) {
        for(Login source : sourceList) {
            String app = source.getApplication().toLowerCase();
            if (!app.equals("web") && !app.equals("mobile")) {
                source.setApplication("other:" + source.getApplication());
            }
        }
        return sourceList;
    }
    @Autowired
    @Qualifier("dateConverter")
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
