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
package com.example.achievement_tracker.service;

import com.example.achievement_tracker.api.dto.AchievementDTO;
import java.util.List;
import java.util.Optional;

public interface AchievementService {
    AchievementDTO createAchievement(AchievementDTO achievementDTO);

    Optional<AchievementDTO> getAchievementById(Long id);

    List<AchievementDTO> getAllAchievements();

    Optional<AchievementDTO> updateAchievement(AchievementDTO achievementDTO);

    void deleteAchievement(Long id);

    Optional<AchievementDTO> getAchievementByTitle(String title);
}
