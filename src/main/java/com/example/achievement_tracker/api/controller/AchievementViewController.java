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
import com.example.achievement_tracker.service.AchievementService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/achievements")
public class AchievementViewController {

    private final AchievementService achievementService;

    public AchievementViewController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public String showAchievements(Model model) {
        List<AchievementDTO> achievements = achievementService.getAllAchievements();
        model.addAttribute("achievements", achievements.isEmpty() ? List.of() : achievements);
        return "achievements";
    }
}
