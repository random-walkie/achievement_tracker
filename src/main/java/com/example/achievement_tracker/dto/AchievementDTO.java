package com.example.achievement_tracker.dto;

import lombok.Getter;

@Getter
public class AchievementDTO {
    private Long id;
    private String title;
    private String description;
    private String dateStarted;
    private String dateCompleted;
    private String tags;
    private String status;

    public AchievementDTO() {
    }

    public AchievementDTO(Long id, String title, String description, String dateStarted, String dateCompleted, String tags, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateStarted = dateStarted;
        this.dateCompleted = dateCompleted;
        this.tags = tags;
        this.status = status;
    }

}
