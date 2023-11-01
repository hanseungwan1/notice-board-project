package com.example.springnews.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String writer;
    private String title;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name="writeDate")
    private LocalDateTime writeDate;

    private int cnt;

    @PrePersist
    public void prePersist() {
        if (writeDate == null) {
            writeDate = LocalDateTime.now();
        }
    }
}
