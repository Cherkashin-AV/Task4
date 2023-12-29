package ru.vtb.javaCourse.Task4.ServiceWithMock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;
import ru.vtb.javaCourse.Task4.Repository.LoginRepo;
import ru.vtb.javaCourse.Task4.Repository.UserRepo;
import ru.vtb.javaCourse.Task4.Service.Task4Service;

import java.time.LocalDateTime;

import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = {TestConfiguration.class})
class Task4ServiceTest {

    @Autowired
    Task4Service service;
    @Autowired
    LoginRepo loginRepo; // = Mockito.mock();
    @Autowired
    UserRepo userRepo; // = Mockito.mock();

    @Test
    public void testService(){
        Login login = new Login(null, LocalDateTime.now(), new User(null, "UserName", "F I O"), "web");;
        service.saveLogin(login);
        Assertions.assertEquals(1, service.getUserList().size());
        Assertions.assertEquals(Integer.valueOf(1), service.getUserList().values().iterator().next());

        login = new Login(null, LocalDateTime.now(), new User(null, "UserName", "F I O"), "web");
        service.saveLogin(login);
        Assertions.assertEquals(1, service.getUserList().size());
        Assertions.assertEquals(Integer.valueOf(1), service.getUserList().values().iterator().next());

        login = new Login(null, LocalDateTime.now(), new User(null, "UserName1", "F I O"), "web");
        service.saveLogin(login);
        Assertions.assertEquals(2, service.getUserList().size());
        Assertions.assertTrue(service.getUserList().values().contains(Integer.valueOf(2)));
    }
}