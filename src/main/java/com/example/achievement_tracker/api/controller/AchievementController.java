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

import com.example.achievement_tracker.api.dto.AchievementDTO;
import com.example.achievement_tracker.api.dto.CreateAchievementDTO;
import com.example.achievement_tracker.api.dto.UpdateAchievementDTO;
import com.example.achievement_tracker.service.AchievementService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AchievementDTO> createAchievement(
            @Valid @RequestBody CreateAchievementDTO createAchievementDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(achievementService.createAchievement(createAchievementDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementDTO> getAchievementById(@PathVariable Long id) {
        Optional<AchievementDTO> achievementDTO = achievementService.getAchievementById(id);
        return achievementDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<List<AchievementDTO>> getAllAchievements() {
        List<AchievementDTO> achievementsDTO = achievementService.getAllAchievements();

        return achievementsDTO.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(achievementsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AchievementDTO> updateAchievement(
            @Valid @RequestBody UpdateAchievementDTO updateAchievementDTO) {
        Optional<AchievementDTO> updatedAchievementDTO =
                achievementService.updateAchievement(updateAchievementDTO);
        return updatedAchievementDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
}
