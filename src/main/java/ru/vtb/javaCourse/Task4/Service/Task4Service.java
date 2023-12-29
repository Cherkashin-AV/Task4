package ru.vtb.javaCourse.Task4.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;
import ru.vtb.javaCourse.Task4.Repository.LoginRepo;
import ru.vtb.javaCourse.Task4.Repository.UserRepo;

import java.util.HashMap;
import java.util.Map;

@Component
public class Task4Service {
    UserRepo userRepo;
    LoginRepo loginRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setLoginRepo(LoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }

    public Map<String, Integer> getUserList() {
        return new HashMap<>(userList);
    }

    Map<String, Integer> userList = new HashMap();
    public User getUser(User user){
        Integer cachedId = userList.get(user.getUserName());
        if (cachedId==null){
            cachedId = userRepo.findIdByUserName(user.getUserName());
            //Нет в кэше, но есть в БД - сохраняем в кэш
            if (cachedId==null) {
                userRepo.save(user);
                cachedId = user.getId();
            }
            userList.put(user.getUserName(), cachedId);
        }
        user.setId(cachedId);
        return user;
    }

    @Transactional
    public void saveLogin(Login login){
        login.setUser(getUser(login.getUser()));
        loginRepo.save(login);
    }

}
