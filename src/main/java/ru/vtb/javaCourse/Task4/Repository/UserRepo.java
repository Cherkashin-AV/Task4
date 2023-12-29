package ru.vtb.javaCourse.Task4.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.javaCourse.Task4.Entity.User;

public interface UserRepo extends CrudRepository<User, Integer> {
    User findByuserName(String username);
    @Query("Select u.id from User u where u.userName = ?1")
    public Integer findIdByUserName(String userName);

    public User findByUserName(String userName);

}
