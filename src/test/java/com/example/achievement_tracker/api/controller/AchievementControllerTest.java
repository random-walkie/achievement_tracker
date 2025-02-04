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
package com.example.achievement_tracker.api.controller;

import com.example.achievement_tracker.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
/*
The @WebMvcTest annotation is used to test the controller layer of the application.
See: https://www.baeldung.com/spring-mockmvc-vs-webmvctest
*/
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/*
The @MockitoBean annotation is used to create a Mockito mock for a specific bean.
 */

@WebMvcTest(controllers = AchievementController.class)
class AchievementControllerUnitTest {

  @MockitoBean private AchievementService achievementService;

  @Autowired private MockMvc mockMvc;

  @BeforeEach
  void setUp() {}

  @Test
  void createAchievement() {}

  @Test
  void getAchievementById() {}

  @Test
  void getAllAchievements() {}

  @Test
  void updateAchievement() {}

  @Test
  void deleteAchievement() {}
}
