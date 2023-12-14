package com.project.webxaydung.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column(name = "name",length = 100, nullable = false)
    private  String name ;
    @Column(name = "code",length = 100, nullable = false)
    private  String code ;
}
