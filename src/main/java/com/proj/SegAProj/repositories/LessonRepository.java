package com.proj.SegAProj.repositories;

import com.proj.SegAProj.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {}
