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
package com.example.achievement_tracker.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.achievement_tracker.persistence.model.Achievement;
import com.example.achievement_tracker.persistence.model.StatusEnum;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class AchievementRepositoryTest {

    @Autowired private AchievementRepository achievementRepository;
    private Achievement achievement1;
    private Achievement achievement2;

    private String title1;
    private StatusEnum status1;

    private String title2;
    private StatusEnum status2;

    @BeforeEach
    void setUp() {
        // Initialize test data
        title1 = "Test Achievement";
        status1 = StatusEnum.COMPLETED;

        achievement1 = Achievement.builder().status(status1).title(title1).build();
        achievementRepository.save(achievement1);

        title2 = "Test Achievement 2";
        status2 = StatusEnum.IN_PROGRESS;
        achievement2 =
                Achievement.builder()
                        .status(status2)
                        .title(title2)
                        .tags(List.of("tagA", "tagB"))
                        .build();
        achievementRepository.save(achievement2);
    }

    @AfterEach
    public void tearDown() {
        // Clean up test data after each test
        achievementRepository.deleteAll();
    }

    @Test
    @DisplayName(
            "Given an achievement exists, when finding all achievements, then all achievements"
                    + " should be retrievable.")
    void findAllWithProperties() {
        List<Achievement> achievementList = achievementRepository.findAll();

        assertEquals(2, achievementList.size(), "Expected 2 achievements in the database");

        // Check properties of the first achievement
        Achievement achievement1 = achievementList.get(0);
        assertEquals(
                title1, achievement1.getTitle(), "Title of the first achievement should match");
        assertEquals(
                status1, achievement1.getStatus(), "Status of the first achievement should match");

        // Check properties of the second achievement
        Achievement achievement2 = achievementList.get(1);
        assertEquals(
                title2, achievement2.getTitle(), "Title of the second achievement should match");
        assertEquals(
                status2, achievement2.getStatus(), "Status of the second achievement should match");
    }

    @Test
    @DisplayName(
            "Given an achievement exists, when finding by title, then the achievement should be"
                    + " retrievable.")
    void findByTitle() {
        Optional<Achievement> optionalAchievement = achievementRepository.findByTitle(title1);
        Achievement foundAchievement =
                optionalAchievement.orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "Achievement with title '" + title1 + "' not found"));

        assertEquals(title1, foundAchievement.getTitle(), "Title should match the expected");
        assertEquals(status1, foundAchievement.getStatus(), "Status should match the expected");
    }

    @Test
    @DisplayName(
            "Given an achievement exists, when finding by status, then the achievement should be"
                    + " retrievable.")
    void findByStatus() {
        Optional<Achievement> optionalAchievement = achievementRepository.findByStatus(status1);
        Achievement foundAchievement =
                optionalAchievement.orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "Achievement with status '" + status1 + "' not found"));

        assertEquals(status1, foundAchievement.getStatus(), "Status should match the expected");
        assertEquals(title1, foundAchievement.getTitle(), "Title should match the expected");
    }

    @Test
    @DisplayName(
            "Given an achievement is saved, when fetching by dynamic ID, then the achievement"
                    + " should match.")
    void findById() {
        // Creating and saving a new achievement to avoid relying on hardcoded IDs
        Optional<Achievement> optionalAchievement =
                achievementRepository.findById(achievement1.getId());
        Achievement foundAchievement =
                optionalAchievement.orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "Achievement with ID '"
                                                + achievement1.getId()
                                                + "' not found"));

        assertEquals(
                achievement1.getId(), foundAchievement.getId(), "ID should match the expected");
    }

    @Test
    @DisplayName(
            "Given a title doesn't exist, when finding by title, then an empty result should return"
                    + " and exception should be thrown.")
    void findByNonExistentTitle() {
        Optional<Achievement> optionalAchievement =
                achievementRepository.findByTitle("Non-Existent Title");

        assertFalse(
                optionalAchievement.isPresent(), "Optional should be empty for non-existent title");

        // Attempting to unwrap and assert exception
        assertThrows(
                NoSuchElementException.class,
                () ->
                        optionalAchievement.orElseThrow(
                                () ->
                                        new NoSuchElementException(
                                                "Achievement with title 'Non-Existent Title' not"
                                                        + " found")));
    }

    @Test
    @DisplayName(
            "Given an achievement has default dates, when saving, then default date values should"
                    + " be correctly set.")
    void verifyDefaultDates() {
        Achievement savedAchievement = achievementRepository.save(achievement1);

        assertEquals(
                LocalDate.now(),
                savedAchievement.getDateStarted(),
                "dateStarted should default to today");
        assertEquals(
                LocalDate.now(),
                savedAchievement.getDateCompleted(),
                "dateCompleted should default to today");
    }

    @Test
    @DisplayName(
            "Given an achievement has tags, when saving and retrieving, then the tags should match"
                    + " exactly.")
    void saveAndRetrieveWithTags() {
        Optional<Achievement> optionalAchievement =
                achievementRepository.findById(achievement2.getId());
        Achievement foundAchievement =
                optionalAchievement.orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "Achievement with ID '"
                                                + achievement2.getId()
                                                + "' not found"));

        assertEquals(
                List.of("tagA", "tagB"),
                foundAchievement.getTags(),
                "Tags should match the saved list");
    }

    @Test
    @DisplayName(
            "Given an achievement is saved, when deleting by ID, then the achievement should be"
                    + " removed.")
    void deleteById() {
        achievementRepository.deleteById(achievement1.getId());
        assertFalse(
                achievementRepository.findById(achievement1.getId()).isPresent(),
                "Achievement should be deleted");
    }

    @Test
    @DisplayName(
            "Given an achievement is updated, when achievement is saved, then the achievement"
                    + " should be updated.")
    void updateAchievement() {
        achievement1.setTitle("Updated Title");
        achievement1.setStatus(StatusEnum.IN_PROGRESS);
        achievementRepository.save(achievement1);

        Optional<Achievement> optionalAchievement =
                achievementRepository.findById(achievement1.getId());
        Achievement foundAchievement =
                optionalAchievement.orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "Achievement with ID '"
                                                + achievement1.getId()
                                                + "' not found"));

        assertEquals("Updated Title", foundAchievement.getTitle(), "Title should be updated");
        assertEquals(
                StatusEnum.IN_PROGRESS, foundAchievement.getStatus(), "Status should be updated");
    }
}
