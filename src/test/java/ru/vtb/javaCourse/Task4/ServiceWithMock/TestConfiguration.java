package ru.vtb.javaCourse.Task4.ServiceWithMock;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;
import ru.vtb.javaCourse.Task4.Repository.LoginRepo;
import ru.vtb.javaCourse.Task4.Repository.UserRepo;

import java.time.LocalDateTime;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {
    @MockBean
    LoginRepo loginRepo;
    @MockBean
    UserRepo userRepo;

    @PostConstruct
    void postConstructor(){
        //doAnswer позволяет определить возвращяемое значение
        Mockito.doAnswer(invocation -> {
            String userName = invocation.getArgument(0);
            if (userName.equals("UserName"))
                return Integer.valueOf(1);
            else
                return null;
        }).when(userRepo).findIdByUserName(Mockito.any());
        Mockito.doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(2);
            return user;
        }).when(userRepo).save(Mockito.any());
        Mockito.doAnswer(invocation -> {
            Login login = invocation.getArgument(0);
            return login;
        }).when(loginRepo).save(Mockito.any());
    }
}
