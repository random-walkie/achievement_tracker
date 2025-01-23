package com.example.achievement_tracker.persistence.mapper.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.achievement_tracker.api.dto.AchievementDTO;
import com.example.achievement_tracker.persistence.model.Achievement;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AchievementMapperImplTest {

  private AchievementMapperImpl achievementMapper;
  private AchievementDTO dto;

  @BeforeEach
  void setUp() {
    achievementMapper = new AchievementMapperImpl();
    dto =
            new AchievementDTO(
                    1L,
                    "Title",
                    "Description",
                    LocalDate.now(),
                    LocalDate.now(),
                    Arrays.asList("tag1", "tag2"),
                    "IN_PROGRESS");
  }

  @Test
  @DisplayName(
          "Given a valid AchievementDTO, when toEntity is called, then it maps the title correctly")
  void testToEntityTitleMapping() {
    Achievement achievement = achievementMapper.toEntity(dto);
    assertEquals(dto.title(), achievement.getTitle());
  }

  @Test
  @DisplayName(
          "Given a valid AchievementDTO, when toEntity is called, then it maps the description correctly")
  void testToEntityDescriptionMapping() {
    Achievement achievement = achievementMapper.toEntity(dto);
    assertEquals(dto.description(), achievement.getDescription());
  }

  @Test
  @DisplayName(
          "Given a valid AchievementDTO, when toEntity is called, then it maps the dateStarted correctly")
  void testToEntityDateStartedMapping() {
    Achievement achievement = achievementMapper.toEntity(dto);
    assertEquals(dto.dateStarted(), achievement.getDateStarted());
  }

  @Test
  @DisplayName(
          "Given a valid AchievementDTO, when toEntity is called, then it maps the dateCompleted correctly")
  void testToEntityDateCompletedMapping() {
    Achievement achievement = achievementMapper.toEntity(dto);
    assertEquals(dto.dateCompleted(), achievement.getDateCompleted());
  }

  @Test
  @DisplayName(
          "Given a valid AchievementDTO, when toEntity is called, then it maps the tags correctly")
  void testToEntityTagsMapping() {
    Achievement achievement = achievementMapper.toEntity(dto);
    assertEquals(dto.tags(), achievement.getTags());
  }

  @Test
  @DisplayName(
          "Given a valid AchievementDTO, when toEntity is called, then it maps the status correctly")
  void testToEntityStatusMapping() {
    Achievement achievement = achievementMapper.toEntity(dto);
    assertEquals(dto.status(), achievement.getStatus().name());
  }

  @Test
  @DisplayName("Given a null AchievementDTO, when toEntity is called, then it should return null")
  void testToEntityNullDTO() {
    AchievementDTO dto = null;
    IllegalArgumentException exception =
            assertThrows(
                    IllegalArgumentException.class,
                    () -> achievementMapper.toEntity(dto));

    assertEquals("Input DTO cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Given a null Achievement, when toDTO is called, then it should return null")
  void testToDTONullEntity() {
    Achievement achievement = null;
    IllegalArgumentException exception =
            assertThrows(
                    IllegalArgumentException.class,
                    () -> achievementMapper.toDTO(achievement));

    assertEquals("Input entity cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName(
          "Given an AchievementDTO with invalid status, when toEntity is called, then it throws IllegalArgumentException")
  void testToEntityInvalidStatus() {
    AchievementDTO invalidDto =
            new AchievementDTO(
                    1L,
                    "Title",
                    "Description",
                    LocalDate.now(),
                    LocalDate.now(),
                    Arrays.asList("tag1", "tag2"),
                    "WRONG_STATUS");
    assertThrows(IllegalArgumentException.class, () -> achievementMapper.toEntity(invalidDto));
  }
}