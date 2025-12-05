package com.cyd.xs.entity.Topic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "topics")
public class Topic {
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    private String level; // "A"/"B"/"C"
    private String tag; // professionalShare/firstIntern/graduateJob/newbiePitfall/offerChoice
    private Integer participantCount = 0;
    private Integer interactiveCount = 0;
    private LocalDateTime latestReplyTime;
    private String guideText;
    private String host;
    private LocalDateTime createdAt;

   }
