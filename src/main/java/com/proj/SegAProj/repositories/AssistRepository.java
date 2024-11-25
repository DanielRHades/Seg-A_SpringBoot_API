package com.proj.SegAProj.repositories;

import com.proj.SegAProj.models.UserLessonAssist;
import com.proj.SegAProj.models.UserLessonAssistKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssistRepository extends JpaRepository<UserLessonAssist, UserLessonAssistKey> {
    List<UserLessonAssist> findByLessonAssistId(Long lessonId);
}
