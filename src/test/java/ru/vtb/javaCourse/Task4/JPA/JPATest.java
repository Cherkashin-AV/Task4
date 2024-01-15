package ru.vtb.javaCourse.Task4.JPA;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.config.BootstrapMode;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;
import ru.vtb.javaCourse.Task4.Repository.LoginRepo;
import ru.vtb.javaCourse.Task4.Repository.UserRepo;
import ru.vtb.javaCourse.Task4.Service.Task4Service;
import ru.vtb.javaCourse.Task4.Writer.DBWriter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@DataJpaTest(showSql = true)
class JPATest {
    @Autowired
    UserRepo userRepo;
    @Autowired
    LoginRepo loginRepo;

    @Test
    @DisplayName("Репозиторий UserRepo")
    public void testUserRepo(){
        User user1 = new User(null, "UserName", "FIO");
        userRepo.save(user1);
        Assertions.assertTrue(user1.getId() != null);
        User user = userRepo.findById(user1.getId()).orElse(null);
        Assertions.assertEquals(user1.getId(), user.getId());
        Integer id = userRepo.findIdByUserName(user1.getUserName());
        Assertions.assertEquals(user1.getId(), id);
    }

    @Test
    @DisplayName("Репозиторий LoginRepo")
    public void testLoginRepo() {
        User user = new User(null, "UserName", "FIO");
        Login login = new Login(null, LocalDateTime.now(), user, "app");
        userRepo.save(user);
        loginRepo.save(login);
        Assertions.assertEquals(Integer.valueOf(1), login.getUser().getId());
        Assertions.assertEquals(Integer.valueOf(1), login.getId());
        Assertions.assertEquals(login.getUser(), userRepo.findById(1).get());
    }

    @Test
    @DisplayName("Тестирование сервиса DataJpa")
    public void testService(){
        Task4Service service = new Task4Service(userRepo, loginRepo);
        Map serviceCache;

        User user1 = new User(null, "UserName1", "FIO1");
        Login login1 = new Login(null, LocalDateTime.now(), user1, "app");

        //Записи нет в кэше. Сохраняем в базу и кэшируем
        service.saveLogin(login1);
        serviceCache = service.getUserList();
        Assertions.assertTrue(serviceCache.size() == 1);
        Assertions.assertTrue(serviceCache.containsKey(user1.getUserName()));
        Assertions.assertEquals(serviceCache.get(user1.getUserName()), user1.getId());
        Integer i = userRepo.findIdByUserName(user1.getUserName());
        Assertions.assertTrue( i != null);

        //Запись уже есть в БД, заносим в кэш
        User user2 = new User(null, "UserName2", "FIO2");
        Login login2 = new Login(null, LocalDateTime.now(), user2, "app");
        userRepo.save(user2);
        i = userRepo.findIdByUserName(user2.getUserName());
        Assertions.assertTrue( i != null);
        service.saveLogin(login2);
        serviceCache = service.getUserList();
        Assertions.assertEquals(2, serviceCache.size());
        Assertions.assertTrue(serviceCache.containsKey(user2.getUserName()));
        Assertions.assertEquals(serviceCache.get(user2.getUserName()), user2.getId());

        //Запись есть в кэше.
        service.saveLogin(login2);
        serviceCache = service.getUserList();
        Assertions.assertEquals(2, serviceCache.size());
        Assertions.assertTrue(serviceCache.containsKey(user2.getUserName()));
        Assertions.assertEquals(serviceCache.get(user2.getUserName()), user2.getId());
    }

    @Test
    @DisplayName("Тестирование DBWriter")
    void testDBWriter(){
        Task4Service service = new Task4Service(userRepo, loginRepo);

        DBWriter dbWriter = new DBWriter(service);

        long loginCnt = loginRepo.count();
        long userCnt = userRepo.count();

        Login loginDB = new Login(null, LocalDateTime.now(), new User(null, "UserNameDB", "FIO2"), "app");
        dbWriter.write(List.of(loginDB));

        Assertions.assertEquals(loginCnt+1, loginRepo.count());
        Assertions.assertEquals(userCnt+1, userRepo.count());

    }
}