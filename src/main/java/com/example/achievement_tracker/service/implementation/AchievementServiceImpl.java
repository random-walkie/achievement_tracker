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
package com.example.achievement_tracker.service.implementation;

import com.example.achievement_tracker.api.dto.AchievementDTO;
import com.example.achievement_tracker.common.exception.RecordAlreadyExistsException;
import com.example.achievement_tracker.common.exception.RecordDoesNotExistException;
import com.example.achievement_tracker.persistence.model.Achievement;
import com.example.achievement_tracker.persistence.repository.AchievementRepository;
import com.example.achievement_tracker.service.AchievementService;
import com.example.achievement_tracker.utility.AchievementMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class AchievementServiceImpl implements AchievementService {
    private final AchievementMapper achievementMapper;
    private final AchievementRepository achievementRepository;

    @Override
    public AchievementDTO createAchievement(AchievementDTO achievementDTO) {
        // check if achievement with the given ID already exists
        Long id = achievementDTO.id();
        if (achievementRepository.existsById(id)) {
            throw new RecordAlreadyExistsException(
                    "Achievement with ID " + id + " already exists!");
        }
        // map DTO to entity
        Achievement achievement = achievementMapper.toEntity(achievementDTO);
        // save entity in a database
        Achievement savedAchievement = achievementRepository.save(achievement);
        // map entity to DTO
        return achievementMapper.toDTO(savedAchievement);
    }

    @Override
    public Optional<AchievementDTO> getAchievementById(Long id) {

        return Optional.ofNullable(
                achievementRepository
                        .findById(id)
                        .map(achievementMapper::toDTO)
                        .orElseThrow(
                                () ->
                                        new RecordDoesNotExistException(
                                                "Achievement with ID " + id + " does not exist!")));
    }

    @Override
    public List<AchievementDTO> getAllAchievements() {
        // find all achievements
        return achievementRepository.findAll().stream()
                .map(achievementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AchievementDTO updateAchievement(AchievementDTO achievementDTO) {
        // check if achievement with the given ID exists
        Long id = achievementDTO.id();
        Achievement existingAchievement =
                achievementRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new RecordDoesNotExistException(
                                                "Achievement with ID " + id + " does not exist!"));
        // update DTO to entity
        achievementMapper.updateEntityFromDTO(achievementDTO, existingAchievement);
        // save entity in a database
        Achievement savedAchievement = achievementRepository.save(existingAchievement);
        // map entity to DTO
        return achievementMapper.toDTO(savedAchievement);
    }

    @Override
    public void deleteAchievement(Long id) {
        if (!achievementRepository.existsById(id)) {
            throw new RecordDoesNotExistException(
                    "Cannot delete: Achievement with ID " + id + " does not exist!");
        }
        achievementRepository.deleteById(id);
    }
}
