package com.project.webxaydung.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "candidates")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column(name = "name",length = 100)
    private  String name ;
    @Column(name = "email",length = 100)
    private String email;
    @Column(name = "phone",length = 100)
    private  String phone;
    @Column(name = "address",length = 100)
    private  String address;
    @Column(name = "cv_file")
    private  byte[] cv_file;
    @ManyToOne
    @JoinColumn(name = "job_opening_id")
    private JobOpening jobOpening;
    @Column(name = "sub_date")
    private LocalDateTime sub_date;
    @PrePersist
    protected void oncreate(){
        sub_date=LocalDateTime.now();
    }
}
