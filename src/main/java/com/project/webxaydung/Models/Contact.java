package com.project.webxaydung.Models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "contacts")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id ;
    @Column(name = "name",length = 100)
    private  String name;
    @Column(name = "address",length = 100)
    private  String address;
    @Column(name = "phone",nullable = false,length = 100)
    private  String phone;
    @Column(name = "email",length = 100)
    private  String email;
    @Column(name = "message")
    private  String message;
    @Column(name = "subdate")
    private  LocalDateTime  subDate;
    @PrePersist
    protected void oncreate(){
        subDate=LocalDateTime.now();
    }

}
