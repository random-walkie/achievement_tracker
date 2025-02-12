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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.achievement_tracker.api.dto.AchievementDTO;
import com.example.achievement_tracker.api.dto.CreateAchievementDTO;
import com.example.achievement_tracker.api.dto.UpdateAchievementDTO;
import com.example.achievement_tracker.exception.RecordDoesNotExistException;
import com.example.achievement_tracker.service.AchievementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AchievementController.class)
class AchievementControllerUnitTest {

    // The @MockitoBean annotation is used to create a Mockito mock for a specific bean.
    @MockitoBean private AchievementService achievementServiceMock;

    // The @Autowired annotation is used to inject the MockMvc instance into the test class.
    @Autowired private MockMvc mockMvc;

    private AchievementDTO achievementDTO;
    private CreateAchievementDTO createAchievementDTO;
    private UpdateAchievementDTO updateAchievementDTO;

    // The ObjectMapper object is used to serialize and deserialize JSON objects.
    private ObjectMapper objectMapper;
    private String title;
    private String status;
    private Long id;

    @BeforeEach
    void setUp() {
        title = "This is a Title";
        status = "IN_PROGRESS";
        id = 1L;
        // The AchievementDTO, CreateAchievementDTO, and UpdateAchievement objects are used to
        // represent the expected
        // request and response payloads.
        createAchievementDTO = CreateAchievementDTO.builder().title(title).status(status).build();
        achievementDTO = AchievementDTO.builder().id(id).title(title).status(status).build();
        updateAchievementDTO =
                UpdateAchievementDTO.builder().id(id).title(title).status("COMPLETED").build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName(
            "Given a valid CreateAchievementDTO, when creating an achievement, then it should"
                    + " return a response with status code 201.")
    void createAchievement() throws Exception {
        // Given: Stub the service to return a predefined achievementDTO
        when(achievementServiceMock.createAchievement(any(CreateAchievementDTO.class)))
                .thenReturn(achievementDTO);
        // When & Then: Perform a POST request to the /api/v1/achievements endpoint with a valid
        // CreateAchievementDTO
        mockMvc.perform(
                        post("/api/v1/achievements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createAchievementDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // jsonPath assertions ensure the response structure and content align with
                // expectations.
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.status").value(status));

        // Verify that the createAchievement method was called once with any CreateAchievementDTO
        verify(achievementServiceMock, times(1)).createAchievement(any(CreateAchievementDTO.class));
    }

    @Test
    @DisplayName(
            "Given a null title value, when creating an achievement, then it should return a"
                    + " response with status code 400.")
    void createAchievementWithTitleNullValue() throws Exception {

        // Given: Create a DTO with a null title
        CreateAchievementDTO nullTitleCreateAchievementDTO =
                CreateAchievementDTO.builder().title(null).status(status).build();

        // When & Then: Perform the POST request and expect a 400 Bad Request status
        mockMvc.perform(
                        post("/api/v1/achievements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                nullTitleCreateAchievementDTO)))
                .andExpect(status().isBadRequest());

        // Verify that no interactions with the service occurred due to validation failure
        verifyNoInteractions(achievementServiceMock);
    }

    @Test
    @DisplayName(
            "Given a null status value, when creating an achievement, then it should return a"
                    + " response with status code 400.")
    void createAchievementWithStatusNullValue() throws Exception {
        // Given: Create a DTO with a null status
        CreateAchievementDTO nullStatusCreateAchievementDTO =
                CreateAchievementDTO.builder().title(title).status(null).build();

        // When & Then: Perform the POST request and expect a 400 Bad Request status
        mockMvc.perform(
                        post("/api/v1/achievements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                nullStatusCreateAchievementDTO)))
                .andExpect(status().isBadRequest());

        // Verify that no interactions with the service occurred due to validation failure
        verifyNoInteractions(achievementServiceMock);
    }

    @Test
    @DisplayName(
            "Given an empty title value, when creating an achievement, then it should return a"
                    + " response with status code 400.")
    void createAchievementWithTitleEmptyValue() throws Exception {
        // Given: Create a DTO with an empty title
        CreateAchievementDTO emptyTitleCreateAchievementDTO =
                CreateAchievementDTO.builder().title("").status(status).build();
        // When & Then: Perform the POST request and expect a 400 Bad Request status
        mockMvc.perform(
                        post("/api/v1/achievements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                emptyTitleCreateAchievementDTO)))
                .andExpect(status().isBadRequest());

        // Verify that no interactions with the service occurred due to validation failure
        verifyNoInteractions(achievementServiceMock);
    }

    @Test
    @DisplayName(
            "Given an empty status value, when creating an achievement, then it should return a"
                    + " response with status code 400.")
    void createAchievementWithStatusEmptyValue() throws Exception {
        // Given: Create a DTO with an empty status
        CreateAchievementDTO emptyStatusCreateAchievementDTO =
                CreateAchievementDTO.builder().title(title).status("").build();
        // When & Then: Perform the POST request and expect a 400 Bad Request status
        mockMvc.perform(
                        post("/api/v1/achievements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                emptyStatusCreateAchievementDTO)))
                .andExpect(status().isBadRequest());

        // Verify that no interactions with the service occurred due to validation failure
        verifyNoInteractions(achievementServiceMock);
    }

    @Test
    @DisplayName(
            "Given a non-null/non-empty, but invalid status type, when creating an achievement,"
                    + " then it should return a response with status code 400.")
    void createAchievementWithInvalidStatusType() throws Exception {
        // Given: Create a DTO with an invalid status type
        CreateAchievementDTO invalidStatusCreateAchievementDTO =
                CreateAchievementDTO.builder().title(title).status("INVALID").build();
        // When & Then: Perform the POST request and expect a 400 Bad Request status
        mockMvc.perform(
                        post("/api/v1/achievements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                invalidStatusCreateAchievementDTO)))
                .andExpect(status().isBadRequest());

        // Verify that no interactions with the service occurred due to validation failure
        verifyNoInteractions(achievementServiceMock);
    }

    @Test
    @DisplayName(
            "Given a valid achievement exists, when updating an achievement, then it should return"
                    + " a response with status code 200.")
    void updateAchievement() throws Exception {
        // Given: Stub the service to return a predefined achievementDTO
        when(achievementServiceMock.createAchievement(any(CreateAchievementDTO.class)))
                .thenReturn(achievementDTO);
        when(achievementServiceMock.updateAchievement(any(UpdateAchievementDTO.class)))
                .thenReturn(Optional.of(achievementDTO));
        // When & Then: Perform a POST request to the /api/v1/achievements endpoint with a valid
        // UpdateAchievementDTO and return a 200 OK status
        mockMvc.perform(
                        put("/api/v1/achievements/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateAchievementDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // jsonPath assertions ensure the response structure and content align with
                // expectations.
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.status").value(status));

        // Verify that the updateAchievement method was called once with any UpdateAchievementDTO
        verify(achievementServiceMock, times(1)).updateAchievement(any(UpdateAchievementDTO.class));
    }

    @Test
    @DisplayName(
            "Given an achievement does not exist, when updating an achievement, then it should"
                    + " return a response with status code 204.")
    void updateAchievementNotFound() throws Exception {
        // Given: Stub the service to return an empty Optional
        when(achievementServiceMock.updateAchievement(any(UpdateAchievementDTO.class)))
                .thenReturn(Optional.empty());
        // When & Then: Perform a POST request to the /api/v1/achievements endpoint with a valid
        // empty Optional and return a 204 No Content status
        mockMvc.perform(
                        put("/api/v1/achievements/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateAchievementDTO)))
                .andExpect(status().isNoContent());

        // Verify that the updateAchievement method was called once with any UpdateAchievementDTO
        verify(achievementServiceMock, times(1)).updateAchievement(any(UpdateAchievementDTO.class));
    }

    @Test
    @DisplayName(
            "Given a valid achievement exists, when fetching an achievement by ID, then it should"
                    + " return a response with status code 200.")
    void getAchievementById() throws Exception {
        // Given: Stub the service to return a predefined achievementDTO
        when(achievementServiceMock.getAchievementById(id)).thenReturn(Optional.of(achievementDTO));
        // When & Then: Perform a GET request to the /api/v1/achievements/{id} endpoint with a valid
        // achievement ID and return a 200 OK status
        mockMvc.perform(
                        get("/api/v1/achievements/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(achievementDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // jsonPath assertions ensure the response structure and content align with
                // expectations.
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.status").value(status));

        // Verify that the getAchievementById method was called once with the given ID
        verify(achievementServiceMock, times(1)).getAchievementById(id);
    }

    @Test
    @DisplayName(
            "Given an achievement does not exist, when fetching an achievement by ID, then it"
                    + " should return a response with status code 204.")
    void getAchievementByIdNotFound() throws Exception {
        // Given: Stub the service to return an empty Optional
        when(achievementServiceMock.getAchievementById(id)).thenReturn(Optional.empty());
        // When & Then: Perform a GET request to the /api/v1/achievements/{id} endpoint with a valid
        // empty Optional and return a 204 No Content status
        mockMvc.perform(
                        get("/api/v1/achievements/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(achievementDTO)))
                .andExpect(status().isNoContent());

        // Verify that the getAchievementById method was called once with the given ID
        verify(achievementServiceMock, times(1)).getAchievementById(id);
    }

    @Test
    @DisplayName(
            "Given a valid achievement exists, when fetching all achievements, then it should"
                    + " return a response with status code 200.")
    void getAllAchievements() throws Exception {
        // Given: Stub the service to return a predefined achievementDTO
        when(achievementServiceMock.getAllAchievements()).thenReturn(List.of(achievementDTO));
        // When & Then: Perform a GET request to the /api/v1/achievements endpoint with a valid
        // achievement ID and return a 200 OK status
        mockMvc.perform(
                        get("/api/v1/achievements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(achievementDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // jsonPath assertions ensure the response structure and content align with
                // expectations.
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].status").value(status));

        // Verify that the getAllAchievements method was called once
        verify(achievementServiceMock, times(1)).getAllAchievements();
    }

    @Test
    @DisplayName(
            "Given no achievement exists, when fetching all achievements, then it should return a"
                    + " response with status code 204.")
    void getAllAchievementsNoContent() throws Exception {
        // Given: Stub the service to return an empty list
        when(achievementServiceMock.getAllAchievements()).thenReturn(List.of());
        // When & Then: Perform a GET request to the /api/v1/achievements endpoint with an empty
        // list and return a 204 No Content status
        mockMvc.perform(
                        get("/api/v1/achievements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(List.of())))
                .andExpect(status().isNoContent());

        // Verify that the getAllAchievements method was called once
        verify(achievementServiceMock, times(1)).getAllAchievements();
    }

    @Test
    @DisplayName(
            "Given a valid achievement exists, when deleting an achievement, then it should return"
                    + " a response with status code 204.")
    void deleteAchievement() throws Exception {
        // When & Then: Perform a DELETE request to the /api/v1/achievements/{id} endpoint with a
        // valid
        // achievement ID and return a 204 No Content status
        mockMvc.perform(
                        delete("/api/v1/achievements/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(achievementDTO)))
                .andExpect(status().isNoContent());

        // Verify that the deleteAchievement method was called once with the given ID
        verify(achievementServiceMock, times(1)).deleteAchievement(id);
    }

    @Test
    @DisplayName(
            "Given an achievement does not exist, when deleting an achievement, then it should"
                    + " return a response with status code 204.")
    void deleteAchievementNotFound() throws Exception {
        // Given: Stub the service to throw an exception
        doThrow(
                        new RecordDoesNotExistException(
                                "Cannot delete: Achievement with ID " + id + " does not exist!"))
                .when(achievementServiceMock)
                .deleteAchievement(id);
        // When & Then: Perform a DELETE request to the /api/v1/achievements/{id} endpoint with an
        // invalid
        // achievement ID and return a 204 No Content status
        mockMvc.perform(
                        delete("/api/v1/achievements/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify that the deleteAchievement method was called once with the given ID
        verify(achievementServiceMock, times(1)).deleteAchievement(id);
    }
}
