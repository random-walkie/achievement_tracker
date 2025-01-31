package com.example.achievement_tracker.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateAchievementDTO(
    @NotBlank @Size(max = 50) String title,
    String description,
    LocalDate dateStarted,
    LocalDate dateCompleted,
    List<String> tags,
    @NotBlank String status) {}
