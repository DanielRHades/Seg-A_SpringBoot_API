package com.proj.SegAProj.services;

import com.proj.SegAProj.models.*;
import com.proj.SegAProj.repositories.AssistRepository;
import com.proj.SegAProj.repositories.LessonRepository;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AssistService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final AssistRepository assistRepository;

    @Autowired
    public  AssistService (UserRepository userRepository, LessonRepository lessonRepository, AssistRepository assistRepository){
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.assistRepository = assistRepository;
    }

    @Transactional
    public UserLessonAssist createAssist(Long userId, Long lessonId, LocalDate entryDate, LocalTime entryTime){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No existe el usuario"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()-> new RuntimeException("No existe la asignatura."));

        UserLessonAssistKey assistKey = new UserLessonAssistKey();
        assistKey.setUserId(userId);
        assistKey.setLessonId(lessonId);
        assistKey.setEntryDate(entryDate);

        UserLessonAssist assist = new UserLessonAssist();
        assist.setId(assistKey);
        assist.setUserAssist(user);
        assist.setLessonAssist(lesson);
        assist.setEntryTime(entryTime);

        user.getLessonAssistListUser().add(assist);
        lesson.getLessonAssistListLesson().add(assist);
        return assistRepository.save(assist);
    }

}
