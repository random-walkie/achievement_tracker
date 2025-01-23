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
package com.example.achievement_tracker.persistence.mapper.implementation;

import com.example.achievement_tracker.api.dto.AchievementDTO;
import com.example.achievement_tracker.persistence.mapper.AchievementMapper;
import com.example.achievement_tracker.persistence.model.Achievement;
import com.example.achievement_tracker.persistence.model.StatusEnum;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class AchievementMapperImpl implements AchievementMapper {

    @Override
    public Achievement toEntity(AchievementDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Input DTO cannot be null");
        }

        StatusEnum status;
        try {
            status = StatusEnum.valueOf(dto.status()); // Ensure validation
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid status provided: " + dto.status());
        }

        return Achievement.builder()
                .title(dto.title())
                .description(dto.description())
                .dateStarted(dto.dateStarted())
                .dateCompleted(dto.dateCompleted())
                .tags(dto.tags() != null ? dto.tags() : new ArrayList<>())
                .status(status)
                .build();
    }

    @Override
    public AchievementDTO toDTO(Achievement entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Input entity cannot be null");
        }

        return AchievementDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .dateStarted(entity.getDateStarted())
                .dateCompleted(entity.getDateCompleted())
                .tags(entity.getTags()) // Remove recursive mapping
                .status(entity.getStatus().name())
                .build();
    }
}
