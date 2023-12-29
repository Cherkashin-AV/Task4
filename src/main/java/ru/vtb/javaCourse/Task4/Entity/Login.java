package ru.vtb.javaCourse.Task4.Entity;


import jakarta.persistence.*;
import lombok.*;

import javax.print.DocFlavor;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "logins")
@ToString
@EqualsAndHashCode
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime access_data;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User    user;
    String  application;
}
