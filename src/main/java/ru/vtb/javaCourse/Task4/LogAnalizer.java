package ru.vtb.javaCourse.Task4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Converters.Convertable;
import ru.vtb.javaCourse.Task4.Entity.Login;
import ru.vtb.javaCourse.Task4.Reader.FileReader;
import ru.vtb.javaCourse.Task4.Reader.Readable;
import ru.vtb.javaCourse.Task4.Service.Task4Service;
import ru.vtb.javaCourse.Task4.Writer.Writable;

import java.nio.charset.Charset;
import java.util.List;

@Component
public class LogAnalizer {
    Readable fileReader;
    @Autowired
    public void setFileReader(Readable fileReader) {
        this.fileReader = fileReader;
    }

    Convertable converter;
    @Autowired
    @Qualifier("FirstConverter")
    public void setConverter(Convertable converter) {
        this.converter = converter;
    }

    Writable writer;
    @Autowired
    public void setWriter(Writable writer) {
        this.writer = writer;
    }

    public void analize(String source){
        List<Login> strings = fileReader.readAll(source);
        while(converter != null){
            converter.covert(strings);
            converter = converter.getNext();
        }
        writer.write(strings);
    }
    public void analize(String source, Charset charset){
        if (fileReader instanceof FileReader)
            ((FileReader)fileReader).setCharset(charset);
        analize(source);
    }

    public void setDBConnection(String jdbcurl, String username, String password){
        writer.init(Task4Service.buildMap(jdbcurl, username, password));
    }
}
