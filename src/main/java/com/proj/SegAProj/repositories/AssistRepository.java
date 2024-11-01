package com.proj.SegAProj.repositories;

import com.proj.SegAProj.models.UserSubjectAssist;
import com.proj.SegAProj.models.UserSubjectAssistKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistRepository extends JpaRepository<UserSubjectAssist, UserSubjectAssistKey> {
}
