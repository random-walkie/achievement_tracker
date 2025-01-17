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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AchievementsTest {
    private static Validator validator;
    private static final Long id = 10239482L;
    private static final String title = "First API";
    private static final String description = "First API description";
    private Achievements achievements;

    @BeforeEach
    void setUp() {
        achievements = new Achievements();
    }

    @Test
    @DisplayName("Correct Instantiation of an Achievements object.")
    void correctInstantiationOfAchievementsObject() {
        assertInstanceOf(Achievements.class, achievements);
    }

    @Test
    @DisplayName("Should not allow to persist null achievements title.")
    void shouldNotAllowToPersistNullTitle() {
      }
}
