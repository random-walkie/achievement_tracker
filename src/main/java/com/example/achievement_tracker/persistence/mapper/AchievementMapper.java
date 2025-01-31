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
package com.example.achievement_tracker.persistence.mapper;

import com.example.achievement_tracker.api.dto.AchievementDTO;
import com.example.achievement_tracker.api.dto.CreateAchievementDTO;
import com.example.achievement_tracker.api.dto.UpdateAchievementDTO;
import com.example.achievement_tracker.persistence.model.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AchievementMapper {
  @Mapping(target = "id", ignore = true)
  Achievement toEntity(CreateAchievementDTO createAchievementDTO);

    AchievementDTO toDTO(Achievement achievement);

  // Auto-generate logic to update an entity using a DTO
  @Mapping(target = "id", ignore = true)
  void updateEntityFromDTO(
      UpdateAchievementDTO updateAchievementDTO, @MappingTarget Achievement entity);
}
