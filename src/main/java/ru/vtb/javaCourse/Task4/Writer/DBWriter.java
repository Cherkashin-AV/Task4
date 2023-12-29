package ru.vtb.javaCourse.Task4.Writer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.SmartPersistenceUnitInfo;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Repository.LoginRepo;
import ru.vtb.javaCourse.Task4.Service.Task4Service;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DBWriter implements Writable{
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

    Task4Service service;

    @Autowired
    public void setService(Task4Service service) {
        this.service = service;
    }

    private void setDB(Map<String, String> map) {
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
            //config.setSchema(map.get("schema"));
            config.setJdbcUrl(config.getJdbcUrl() + "?currentSchema=" + map.get("schema"));
        }
        DataSource newDataSource = new HikariDataSource(config);
        ((MutablePersistenceUnitInfo) entityManagerFactory.getPersistenceUnitInfo()).setNonJtaDataSource(newDataSource);
        entityManagerFactory.setDataSource(newDataSource);
        dataSource = newDataSource;
    }

    @Override
    public void init(Map dest) {
        setDB(dest);
    }

    @Override
    public void write(List<Login> logins) {
        for (Login login : logins)
            service.saveLogin(login);
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
