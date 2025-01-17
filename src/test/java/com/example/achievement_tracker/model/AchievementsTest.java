package com.example.achievement_tracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AchievementsTest {

  private Achievements achievements;

  @BeforeEach
  void setUp() {
    achievements = new Achievements();
  }

  @Test
  @DisplayName("Correct Instantiation of an Achievements object.")
  void correctInstantiationOfAchievementsObject() {

    assertInstanceOf(Achievements.class, achievements);
  }

}