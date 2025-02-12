/*
 * Copyright 2015 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.achievement_tracker.service.implementation;

import com.example.achievement_tracker.api.dto.AchievementDTO;
import com.example.achievement_tracker.api.dto.CreateAchievementDTO;
import com.example.achievement_tracker.api.dto.UpdateAchievementDTO;
import com.example.achievement_tracker.exception.RecordDoesNotExistException;
import com.example.achievement_tracker.persistence.mapper.AchievementMapper;
import com.example.achievement_tracker.persistence.model.Achievement;
import com.example.achievement_tracker.persistence.model.StatusEnum;
import com.example.achievement_tracker.persistence.repository.AchievementRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Transactional
public class AchievementServiceImplTest {

    @InjectMocks private AchievementServiceImpl achievementService;

    @Mock private AchievementRepository achievementRepository;

    @Mock private AchievementMapper achievementMapper;

    private AchievementDTO achievementDTO;
    private CreateAchievementDTO createAchievementDTO;
    private UpdateAchievementDTO updateAchievementDTO;
    private Achievement achievement;

    @BeforeEach
    void setUp() {
        Long id = 1L;
        String title = "Test Title";
        String description = "Test Description";
        LocalDate dateStarted = LocalDate.now();
        LocalDate dateCompleted = LocalDate.now();
        List<String> tags = Arrays.asList("tag1", "tag2");
        StatusEnum status = StatusEnum.COMPLETED;

        achievementDTO =
                AchievementDTO.builder()
                        .id(id)
                        .title(title)
                        .description(description)
                        .dateStarted(dateStarted)
                        .dateCompleted(dateCompleted)
                        .tags(tags)
                        .status(status.name()) // StatusEnum to String
                        .build();

        createAchievementDTO =
                CreateAchievementDTO.builder()
                        .title(title)
                        .description(description)
                        .dateStarted(dateStarted)
                        .dateCompleted(dateCompleted)
                        .tags(tags)
                        .status(status.name()) // StatusEnum to String
                        .build();

        updateAchievementDTO =
                UpdateAchievementDTO.builder()
                        .id(id)
                        .title(title)
                        .description(description)
                        .dateStarted(dateStarted)
                        .dateCompleted(dateCompleted)
                        .tags(tags)
                        .status(status.name()) // StatusEnum to String
                        .build();

        achievement =
                Achievement.builder()
                        .id(id)
                        .title(title)
                        .description(description)
                        .dateStarted(dateStarted)
                        .dateCompleted(dateCompleted)
                        .tags(tags)
                        .status(status)
                        .build();
    }

    @Test
    @DisplayName("Should create an achievement and return AchievementDTO")
    void createAchievement_Success() {
        Mockito.when(achievementMapper.toEntity(createAchievementDTO)).thenReturn(achievement);
        Mockito.when(achievementRepository.save(achievement)).thenReturn(achievement);
        Mockito.when(achievementMapper.toDTO(achievement)).thenReturn(achievementDTO);

        AchievementDTO result = achievementService.createAchievement(createAchievementDTO);

        Mockito.verify(achievementRepository, Mockito.times(1))
                .save(Mockito.any(Achievement.class));
        Assertions.assertEquals(
                achievementDTO, result, "Created AchievementDTO should match the expected value.");
    }

    @Test
    @DisplayName("Should return AchievementDTO for a valid ID")
    void getAchievementById_Success() {
        Mockito.when(achievementRepository.findById(achievement.getId()))
                .thenReturn(Optional.of(achievement));
        Mockito.when(achievementMapper.toDTO(achievement)).thenReturn(achievementDTO);

        Optional<AchievementDTO> result =
                achievementService.getAchievementById(achievementDTO.id());

        Assertions.assertTrue(
                result.isPresent(), "AchievementDTO should be present for a valid ID.");
        Assertions.assertEquals(
                achievementDTO,
                result.get(),
                "Returned AchievementDTO should match the expected value.");
    }

    @Test
    @DisplayName("Should return an empty result for a non-existent ID")
    void getAchievementByNonExistentId() {
        Mockito.when(achievementRepository.findById(123L)).thenReturn(Optional.empty());

        Optional<AchievementDTO> result = achievementService.getAchievementById(123L);

        Assertions.assertFalse(
                result.isPresent(), "AchievementDTO should not be present for a non-existent ID.");
    }

    @Test
    @DisplayName("Should return a list of all AchievementDTOs")
    void getAllAchievements() {
        Mockito.when(achievementRepository.findAll())
                .thenReturn(Collections.singletonList(achievement));
        Mockito.when(achievementMapper.toDTO(achievement)).thenReturn(achievementDTO);

        List<AchievementDTO> result = achievementService.getAllAchievements();

        Assertions.assertEquals(
                1, result.size(), "The size of the result list should be as expected.");
        Assertions.assertEquals(
                achievementDTO,
                result.get(0),
                "The retrieved AchievementDTO should match the expected value.");
    }

    @Test
    @DisplayName("Should update an existing achievement and return updated AchievementDTO")
    void updateAchievement_Success() {
        Mockito.when(achievementRepository.findById(achievement.getId()))
                .thenReturn(Optional.of(achievement));
        Mockito.when(achievementRepository.save(achievement)).thenReturn(achievement);
        Mockito.when(achievementMapper.toDTO(achievement)).thenReturn(achievementDTO);

        Optional<AchievementDTO> result =
                achievementService.updateAchievement(updateAchievementDTO);

        Mockito.verify(achievementRepository, Mockito.times(1))
                .save(Mockito.any(Achievement.class));
        Assertions.assertTrue(
                result.isPresent(), "AchievementDTO should be present for a valid ID.");
        Assertions.assertEquals(
                achievementDTO,
                result.get(),
                "Updated AchievementDTO should match the expected value.");
    }

    @Test
    @DisplayName("Should delete an achievement for a valid ID")
    void deleteAchievement_Success() {
        Mockito.when(achievementRepository.existsById(achievement.getId())).thenReturn(true);

        achievementService.deleteAchievement(achievementDTO.id());

        Mockito.verify(achievementRepository, Mockito.times(1)).deleteById(achievementDTO.id());
    }

    @Test
    @DisplayName("Should not perform deletion for a non-existent ID")
    void deleteAchievementWithNonExistentId() {
        Mockito.when(achievementRepository.existsById(123L)).thenReturn(false);

        // Assert that the exception is thrown
        RecordDoesNotExistException exception =
                Assertions.assertThrows(
                        RecordDoesNotExistException.class,
                        () -> achievementService.deleteAchievement(123L));

        // Verify the exception message
        Assertions.assertEquals(
                "Cannot delete: Achievement with ID 123 does not exist!",
                exception.getMessage(),
                "Exception message should match the expected value.");

        // Verify that deleteById() is never called
        Mockito.verify(achievementRepository, Mockito.never()).deleteById(123L);
    }

    @Test
    @DisplayName("Should find achievement by title")
    void getAchievementByTitle_Success() {
        Mockito.when(achievementRepository.findByTitle("Test Title"))
                .thenReturn(Optional.of(achievement));
        Mockito.when(achievementMapper.toDTO(achievement)).thenReturn(achievementDTO);

        Optional<AchievementDTO> result = achievementService.getAchievementByTitle("Test Title");

        Assertions.assertTrue(result.isPresent(), "Achievement should be found by title.");
        Assertions.assertEquals(
                achievementDTO,
                result.get(),
                "The retrieved AchievementDTO should match the expected value.");
    }

    @Test
    @DisplayName("Should return empty for a non-existent title")
    void getAchievementByTitle_NonExistent() {
        Mockito.when(achievementRepository.findByTitle("NonExistent Title"))
                .thenReturn(Optional.empty());

        Optional<AchievementDTO> result =
                achievementService.getAchievementByTitle("NonExistent Title");

        Assertions.assertFalse(
                result.isPresent(), "No Achievement should be found for a non-existent title.");
    }
}
