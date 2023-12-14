package com.project.webxaydung.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "jobopenings")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobOpening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id ;
    @Column(name = "title",length = 255,nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "requirement")
    private String requirement;
    @Column(name = "startday")
    private Date start_day;
    @Column(name = "endday")
    private Date end_day;
    @Column(name = "hiring_needs")
    private int hiring_needs;
    @Column(name = "vacancies")
    private int vacancies;
    @Column(name="is_active")
    private int is_active ;

}
