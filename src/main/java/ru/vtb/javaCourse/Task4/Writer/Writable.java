package ru.vtb.javaCourse.Task4.Writer;


import ru.vtb.javaCourse.Task4.Entity.Login;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public interface Writable {
    void init(Map dest);
    void write(List<Login> logins);
}
