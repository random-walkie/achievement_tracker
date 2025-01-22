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

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder().id(id).title(title).status(status).build();
    }

    @Test
    @DisplayName(
            "Given a valid builder, when instantiating an achievement, then it should be an"
                    + " instance of the Achievement class.")
    void correctInstantiationOfAchievementsObject() {
        assertInstanceOf(Achievement.class, achievement);
    }

    @Test
    @DisplayName(
            "Given a built achievement, when getting the `id`, then the `id` should match the"
                    + " expected value.")
    void idIsBuiltCorrectly() {
        assertEquals(id, achievement.getId());
    }

    @Test
    @DisplayName(
            "Given a built achievement, when getting the `title`, then the `title` should match the"
                    + " expected value.")
    void titleIsBuiltCorrectly() {
        assertEquals(title, achievement.getTitle());
    }

    @Test
    @DisplayName(
            "Given a built achievement, when getting the `status`, then the `status` should match"
                    + " the expected value.")
    void statusIsBuiltCorrectly() {
        assertEquals(status, achievement.getStatus());
    }

    @Test
    @DisplayName(
            "Given a `title` field is missing, when building an achievement, then it should throw a"
                    + " NullPointerException.")
    void checkTitleFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> Achievement.builder().id(id).status(status).build());

        assertEquals("title is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName(
            "Given a `status` field is missing, when building an achievement, then it should throw"
                    + " a NullPointerException.")
    void checkStatusFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> Achievement.builder().id(id).title(title).build());

        assertEquals("status is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName(
            "Given a `description` is not provided, when initializing an achievement, then it"
                    + " should default to the expected value.")
    void checkDefaultValueForDescriptionField() {
        assertEquals("This is a generic achievement.", achievement.getDescription());
    }

    @Test
    @DisplayName(
            "Given `dateStarted` is not provided, when initializing an achievement, then it should"
                    + " default to the current date.")
    void checkDefaultValueForDateStartedField() {
        assertEquals(LocalDate.now(), achievement.getDateStarted());
    }

    @Test
    @DisplayName(
            "Given `dateCompleted` is not provided, when initializing an achievement, then it"
                    + " should default to the current date.")
    void checkDefaultValueForDateCompletedField() {
        assertEquals(LocalDate.now(), achievement.getDateCompleted());
    }

    @Test
    @DisplayName(
            "Given `tags` are not provided, when initializing an achievement, then it should"
                    + " default to an empty list.")
    void checkDefaultValueForTagsField() {
        assertTrue(achievement.getTags().isEmpty(), "Tags list should be empty by default.");
    }
}
