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
import com.example.achievement_tracker.api.dto.CreateAchievementDTO;
import com.example.achievement_tracker.api.dto.UpdateAchievementDTO;
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
  public AchievementDTO createAchievement(CreateAchievementDTO createAchievementDTO) {
    // map DTO to entity
    Achievement achievement = achievementMapper.toEntity(createAchievementDTO);
        // save entity in a database
        Achievement savedAchievement = achievementRepository.save(achievement);
        // map entity to DTO
        return achievementMapper.toDTO(savedAchievement);
    }

    @Override
    public Optional<AchievementDTO> getAchievementById(Long id) {

    return achievementRepository.findById(id).map(achievementMapper::toDTO);
    }

    @Override
    public Optional<AchievementDTO> getAchievementByTitle(String title) {

    return achievementRepository
        .findByTitle(title)
        .map(achievementMapper::toDTO); // Map to DTO if present, otherwise return
    // Optional.empty()
  }

    @Override
    public List<AchievementDTO> getAllAchievements() {
        // find all achievements
        return achievementRepository.findAll().stream()
                .map(achievementMapper::toDTO)
                .collect(Collectors.toList());
    }

  @Override
  public Optional<AchievementDTO> updateAchievement(UpdateAchievementDTO updateAchievementDTO) {
    // Check if achievement with the given ID exists
    Long id = updateAchievementDTO.id();
        Optional<Achievement> existingAchievementOptional = achievementRepository.findById(id);

        if (existingAchievementOptional.isEmpty()) {
            // Return an empty Optional if the achievement does not exist
            return Optional.empty();
        }

        // Update DTO to entity
        Achievement existingAchievement = existingAchievementOptional.get();
    achievementMapper.updateEntityFromDTO(updateAchievementDTO, existingAchievement);

        // Save entity in the database
        Achievement savedAchievement = achievementRepository.save(existingAchievement);

        // Map entity to DTO
        AchievementDTO updatedDTO = achievementMapper.toDTO(savedAchievement);

        // Return updated DTO wrapped in Optional
        return Optional.of(updatedDTO);
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
