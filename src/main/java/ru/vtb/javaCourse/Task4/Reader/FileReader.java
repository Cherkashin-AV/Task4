package ru.vtb.javaCourse.Task4.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Entity.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ReadException extends RuntimeException{
    public ReadException(Throwable cause) {
        super(cause);
    }
}

@Component
public class FileReader implements Readable<String, Login>{
    Charset charset = Charset.defaultCharset();

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public List<Login> readAll(String source) {
        List<Login> result = new ArrayList<>();
        File curFile = new File(source);
        if (curFile.isDirectory()){
            for(File subFile : curFile.listFiles()){
                try {
                    result.addAll(readFile(subFile));
                } catch (IOException e) {
                    throw new ReadException(e);
                }
            }
        }
        else{
            try {
                result.addAll(readFile(curFile));
            } catch (IOException e) {
                throw new ReadException(e);
            }
        }
        return result;
    }

    private List<Login> readFile(File curFile) throws IOException {
        List<Login> result = new ArrayList<>();
        Scanner scanner = new Scanner(curFile, charset);
        while (scanner.hasNextLine()){
            result.add(covertToLogin(scanner.nextLine()));
        }
        return result;
    }

    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    private Login covertToLogin(String source) {
        //Формат строки: Логин Фамилия Имя Отчество дата_входа тип_приложения
        User user = new User();
        Login login = new Login();
        login.setUser(user);

        String[] strings = source.split("\\s");
        user.setUserName(strings[0]);
        user.setFio(String.format("%s %s %s", strings[1], strings[2], strings[3]));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);

        if (!strings[4].isEmpty())
            login.setAccess_data(LocalDateTime.parse(strings[4], dtf));
        login.setApplication(strings[5]);

        return login;
    }
}
