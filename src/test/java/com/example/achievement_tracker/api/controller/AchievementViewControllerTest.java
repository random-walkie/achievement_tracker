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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.achievement_tracker.api.dto.AchievementDTO;
import com.example.achievement_tracker.service.AchievementService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

@WebMvcTest(controllers = AchievementViewController.class)
class AchievementViewControllerTest {
    // The @MockitoBean annotation is used to create a Mockito mock for a specific bean.
    @MockitoBean private AchievementService achievementServiceMock;

    // The @Autowired annotation is used to inject the MockMvc instance into the test class.
    @Autowired private MockMvc mockMvc;

    @MockitoBean private Model model;

    private AchievementDTO achievementDTO;

    private String title;
    private String status;

    @BeforeEach
    void setUp() {
        title = "This is a Title";
        status = "IN_PROGRESS";
        Long id = 1L;
        achievementDTO = AchievementDTO.builder().id(id).title(title).status(status).build();
    }

    @Test
    @DisplayName(
            "Given a valid list of AchievementDTO exists, when showAchievements is called, then the"
                    + " model should contain the list of achievements")
    void showAchievements() throws Exception {
        // Given
        when(achievementServiceMock.getAllAchievements()).thenReturn(List.of(achievementDTO));

        // Perform GET request and verify response
        mockMvc.perform(get("/achievements"))
                .andExpect(status().isOk()) // Ensure response is 200
                .andExpect(view().name("achievements")) // Ensure correct view is returned
                .andExpect(model().attributeExists("achievements")) // Ensure model contains attribute
                .andExpect(model().attribute("achievements", hasSize(1))) // Expect list of size 1
                .andExpect(content().string(containsString(title))) // Expect title in response
                .andExpect(content().string(containsString(status))); // Expect status in response

    }

    @Test
    @DisplayName(
            "Given an empty list of achievements, then the model should contain an empty list of"
                    + " achievements")
    void showAchievements_emptyList() throws Exception {
        // Mock Service to Return Empty List
        when(achievementServiceMock.getAllAchievements()).thenReturn(List.of());

        // Perform GET request and verify response
        mockMvc.perform(get("/achievements"))
                .andExpect(status().isOk()) // Ensure response is 200
                .andExpect(view().name("achievements")) // Ensure correct view is returned
                .andExpect(model().attributeExists("achievements")) // Ensure model contains attribute
                .andExpect(model().attribute("achievements", hasSize(0))) // Expect empty list
                .andExpect(content().string(containsString("No achievements found")));
        }
}
