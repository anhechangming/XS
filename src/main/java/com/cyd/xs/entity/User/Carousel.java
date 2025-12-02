package com.cyd.xs.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "carousels")
public class Carousel {
    @Id
    private String id;

    private String title;
    private String imageUrl;
    private String description;
    private String link;
    private Integer sortOrder;
    private String status; // active/inactive
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}