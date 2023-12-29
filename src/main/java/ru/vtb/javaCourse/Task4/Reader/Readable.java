package ru.vtb.javaCourse.Task4.Reader;

import ru.vtb.javaCourse.Task4.Entity.Login;

import java.util.List;

public interface Readable <T,E> {
    List<E> readAll(T source);
}
