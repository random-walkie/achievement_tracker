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
package com.example.achievement_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Builder
@NoArgsConstructor(force = true) // required by lombok to add force = true
@AllArgsConstructor
public class Achievements {

    // required field
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @NonNull private final Long id;

    // required field
    @Column(columnDefinition = "varchar(50)", nullable = false, unique = true)
    @Length(max = 50) @NonNull private final String title;

    @Column(columnDefinition = "default 'This is a generic achievement.'", nullable = false)
    @Builder.Default
    private String description = "This is a generic achievement.";

    @Column(nullable = false)
    @Builder.Default
    private LocalDate dateStarted = LocalDate.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDate dateCompleted = LocalDate.now();

    @ElementCollection
    @Column(nullable = false)
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    // required field
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NonNull private final StatusEnum status;
}
