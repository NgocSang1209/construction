package com.project.webxaydung.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subscribersemail")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id ;
    @Column(name = "email",nullable = false)
    private  String email;

}
