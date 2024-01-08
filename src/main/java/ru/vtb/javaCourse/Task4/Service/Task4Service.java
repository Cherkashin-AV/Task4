package ru.vtb.javaCourse.Task4.Service;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;
import ru.vtb.javaCourse.Task4.Repository.LoginRepo;
import ru.vtb.javaCourse.Task4.Repository.UserRepo;

import javax.sql.DataSource;
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

    DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public Map<String, Integer> getUserList() {
        return new HashMap<>(userList);
    }

    Map<String, Integer> userList = new HashMap();
    private User getUser(User user){
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

    @Transactional(readOnly = true)
    public long getLoginCount(){
        return loginRepo.count();
    }

    public void setDB(Map<String, String> map) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(((HikariDataSource)dataSource).getDriverClassName());
        if (map.containsKey("jdbcurl")) {
            config.setJdbcUrl(map.get("jdbcurl"));
        }
        if (map.containsKey("username"))
            config.setUsername(map.get("username"));
        if (map.containsKey("password"))
            config.setPassword(map.get("password"));
        if (map.containsKey("schema")) {
            config.setJdbcUrl(config.getJdbcUrl() + "?currentSchema=" + map.get("schema"));
        }
        DataSource newDataSource = new HikariDataSource(config);
        ((MutablePersistenceUnitInfo) entityManagerFactory.getPersistenceUnitInfo()).setNonJtaDataSource(newDataSource);
        entityManagerFactory.setDataSource(newDataSource);
        entityManagerFactory.afterPropertiesSet();
    }

    public static Map<String, String> buildMap(String jdbcurl, String username, String password){
        Map<String, String> map = new HashMap<>();
        if (jdbcurl != null)
            map.put("jdbcurl", jdbcurl);
        if (username != null)
            map.put("username", username);
        if (password != null)
            map.put("password", password);
        return map;
    }


}
