package com.example.demo.domains.aggregate01.repository.po;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "person")
public class UserPo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 60)
    private String email;

    private Integer gender;
    @Column(length = 20)
    private String phone;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

}