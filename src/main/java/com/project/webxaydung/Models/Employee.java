package com.project.webxaydung.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id ;
    @Column(name = "name",length = 100)
    private  String name;
    @Column(name = "position",length = 100)
    private  String position;
    @Column(name = "phone",nullable = false,length = 100)
    private  String phone;
    @Column(name = "email",nullable = false,length = 100)
    private  String email;
}
