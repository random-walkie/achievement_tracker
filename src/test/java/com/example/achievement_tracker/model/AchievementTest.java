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
package com.example.achievement_tracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AchievementTest {
    private Achievement achievement;
    private final Long id = 134L;
    private final String title = "This is a Title";
    private final StatusEnum status = StatusEnum.COMPLETED;
    private final LocalDate expectedDate = LocalDate.of(2025, 1, 21);

    @BeforeEach
    void setUp() {
        achievement =
                com.example.achievement_tracker.model.Achievement.builder()
                        .id(id)
                        .title(title)
                        .status(status)
                        .build();
    }

    @Test
    @DisplayName("Correct Instantiation of an Achievement object.")
    void correctInstantiationOfAchievementsObject() {
        assertInstanceOf(Achievement.class, achievement);
    }

    @Test
    @DisplayName("`id` is built correctly.")
    void idIsBuiltCorrectly() {
        assertEquals(id, achievement.getId());
    }

    @Test
    @DisplayName("`title` is built correctly.")
    void titleIsBuiltCorrectly() {
        assertEquals(title, achievement.getTitle());
    }

    @Test
    @DisplayName("`status` is built correctly.")
    void statusIsBuiltCorrectly() {
        assertEquals(status, achievement.getStatus());
    }

    @Test
    @DisplayName("`id` field is required.")
    void checkIdFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                com.example.achievement_tracker.model.Achievement.builder()
                                        .title(title)
                                        .status(status)
                                        .build());
        assertEquals("id is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("`title` field is required.")
    void checkTitleFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                com.example.achievement_tracker.model.Achievement.builder()
                                        .id(id)
                                        .status(status)
                                        .build());

        assertEquals("title is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("`status` field is required.")
    void checkStatusFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                com.example.achievement_tracker.model.Achievement.builder()
                                        .id(id)
                                        .title(title)
                                        .build());

        assertEquals("status is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("Default value for optional `description` field.")
    void checkDefaultValueForDescriptionField() {
        assertEquals("This is a generic achievement.", achievement.getDescription());
    }

    @Test
    @DisplayName("Default value for optional `dateStarted` field.")
    void checkDefaultValueForDateStartedField() {
        assertEquals(expectedDate, achievement.getDateStarted());
    }

    @Test
    @DisplayName("Default value for optional `dateCompleted` field.")
    void checkDefaultValueForDateCompletedField() {
        assertEquals(expectedDate, achievement.getDateCompleted());
    }

    @Test
    @DisplayName("Default value for optional `tags` field.")
    void checkDefaultValueForTagsField() {
        assertTrue(achievement.getTags().isEmpty());
    }
}
