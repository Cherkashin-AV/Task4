package ru.vtb.javaCourse.Task4.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.javaCourse.Task4.Entity.Login;

public interface LoginRepo extends CrudRepository<Login, Integer> {
}
