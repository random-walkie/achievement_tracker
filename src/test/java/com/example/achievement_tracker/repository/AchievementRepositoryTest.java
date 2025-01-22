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
package com.example.achievement_tracker.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.achievement_tracker.model.Achievement;
import com.example.achievement_tracker.model.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AchievementRepositoryTest {
    @Autowired private AchievementRepository achievementRepository;
    private Achievement achievement;
    private final String title = "Test Achievement";
    private final StatusEnum status = StatusEnum.COMPLETED;

    @BeforeEach
    void setUp(){
        achievement = Achievement.builder().status(status).title(title).build();

        achievementRepository.save(achievement);
    }

    @AfterEach
    public void tearDown() {
        // Release test data after each test method
        achievementRepository.delete(achievement);
    }

    @Test
    @DisplayName("Given an achievement, when the achievement is saved, the achievement can be found by title.")
    void findByTitle() {
        // The repository returns an Optional to safely handle the case where no achievement is found.
        // This prevents returning null and avoids potential NullPointerException,
        // enforcing us to explicitly handle the missing value scenario.
        Optional<Achievement> optionalAchievement = achievementRepository.findByTitle(title);
        Achievement foundAchievement = optionalAchievement.orElseThrow(() ->
                new NoSuchElementException("Achievement with title '" + title + "' not found"));

        assertEquals(title, foundAchievement.getTitle());
    }
    @Test
    @DisplayName("Given an achievement, when the achievement is saved, the achievement can be found by status.")
    void findByStatus() {
        Optional<Achievement> optionalAchievement = achievementRepository.findByStatus(status);
        Achievement foundAchievement = optionalAchievement.orElseThrow(() ->
                new NoSuchElementException("Achievement with status '" + status + "' not found"));

        assertEquals(status, foundAchievement.getStatus());
    }

    @Test
    @DisplayName("Given an achievement, when the achievement is saved, the achievement can be found by id.")
    void findById() {
        Optional<Achievement> optionalAchievement = achievementRepository.findById(1L);
        Achievement foundAchievement = optionalAchievement.orElseThrow(() ->
                new NoSuchElementException("Achievement with id '1' not found"));
        System.out.println(foundAchievement.getId());
        assertEquals(1L, foundAchievement.getId());

    }

}
