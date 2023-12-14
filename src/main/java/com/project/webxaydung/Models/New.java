package com.project.webxaydung.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name ="news")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id ;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "title",length = 255,nullable = false)
    private String title;
    @Column(name = "short_description")
    private String short_description;
    @Column(name = "content",nullable = false)
    private String content;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "date_published")
    private LocalDateTime date_published;
    @PrePersist
    protected void oncreate(){
        date_published=LocalDateTime.now();
    }
}
