package ru.vtb.javaCourse.Task4.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Service.Task4Service;

import java.util.List;
import java.util.Map;

@Component
public class DBWriter implements Writable{

    private Task4Service service;

    @Autowired
    public DBWriter(Task4Service service) {
        this.service = service;
    }

    @Override
    public void init(Map dest) {
        service.setDB(dest);
    }

    @Override
    public void write(List<Login> logins) {
        for (Login login : logins)
            service.saveLogin(login);
    }

}
