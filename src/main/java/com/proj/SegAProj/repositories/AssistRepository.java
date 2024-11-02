package com.proj.SegAProj.repositories;

import com.proj.SegAProj.models.UserLessonAssist;
import com.proj.SegAProj.models.UserLessonAssistKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistRepository extends JpaRepository<UserLessonAssist, UserLessonAssistKey> {
}
