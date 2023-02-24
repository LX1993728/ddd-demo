package com.example.demo.domains.aggregate01.repository.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "address")
public class AddressPo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 关联用户
    private Long userId;

    // 收货人姓名
    @Column(length = 10)
    private String receiver;

    // 收货人电话
    @Column(length = 10)
    private String phone;

    // 收货人地址
    @Column(length = 30)
    private String country;

    @Column(length = 30)
    private String province;

    @Column(length = 30)
    private String city;

    // 住址的详细信息
    @Column(length = 30)
    private String detail;

}