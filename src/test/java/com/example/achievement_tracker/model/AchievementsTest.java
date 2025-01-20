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

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AchievementsTest {
    private Achievements achievements;
    private final Long id = 134L;
    private final String title = "This is a Title";
    private final Date dateStarted = new Date();
    private final StatusEnum status = StatusEnum.COMPLETED;

    @BeforeEach
    void setUp() {
        achievements =
                Achievements.builder()
                        .id(id)
                        .title(title)
                        .dateStarted(dateStarted)
                        .status(status)
                        .build();
    }

    @Test
    @DisplayName("Correct Instantiation of an Achievements object.")
    void correctInstantiationOfAchievementsObject() {
        assertInstanceOf(Achievements.class, achievements);
    }

    @Test
    @DisplayName("Check `id` field is required.")
    void checkIdFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                Achievements.builder()
                                        .title(title)
                                        .dateStarted(dateStarted)
                                        .status(status)
                                        .build());
        assertEquals("id is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("Check `title` field is required.")
    void checkTitleFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                Achievements.builder()
                                        .id(id)
                                        .dateStarted(dateStarted)
                                        .status(status)
                                        .build());

        assertEquals("title is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("Check `dateStarted` field is required.")
    void checkDateStartedFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> Achievements.builder().id(id).title(title).status(status).build());

        assertEquals("dateStarted is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("Check `status` field is required.")
    void checkStatusFieldIsRequired() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                Achievements.builder()
                                        .id(id)
                                        .dateStarted(dateStarted)
                                        .title(title)
                                        .build());

        assertEquals("status is marked non-null but is null", exception.getMessage());
    }

    @Test
    @DisplayName("Check default value for optional `description` field.")
    void checkDefaultValueForDescriptionField() {
        assertEquals("This is a generic achievement.", achievements.getDescription());
    }

    @Test
    @DisplayName("Check default value for optional `dateCompleted` field.")
    void checkDefaultValueForDateCompletedField() {
        // TODO: I am wondering if this is the best way to test this?...
        // when
        String expectedDate = new Date().toString();
        // then
        assertEquals(expectedDate, achievements.getDateCompleted().toString());
    }

    @Test
    @DisplayName("Check default value for optional `tags` field.")
    void checkDefaultValueForTagsField() {
        assertTrue(achievements.getTags().isEmpty());
    }
}
