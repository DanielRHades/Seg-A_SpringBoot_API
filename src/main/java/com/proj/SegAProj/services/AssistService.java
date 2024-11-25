package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.LessonAssistResponse;
import com.proj.SegAProj.models.*;
import com.proj.SegAProj.repositories.AssistRepository;
import com.proj.SegAProj.repositories.LessonRepository;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AssistService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final AssistRepository assistRepository;

    @Autowired
    public  AssistService (UserRepository userRepository,
                           LessonRepository lessonRepository,
                           AssistRepository assistRepository){
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.assistRepository = assistRepository;
    }

    public LessonAssistResponse findAssistsByLessonId(Long id){
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No existe la lección."));
        List<UserLessonAssist> assists = assistRepository.findByLessonAssistId(lesson.getId());
        Map<LocalDate, List<LessonAssistResponse.AssistUserDetail>> assistByDate = assists.stream()
                .collect(Collectors.groupingBy(assist -> assist.getId().getEntryDate(),
                        Collectors.mapping(assist -> {
                            LessonAssistResponse.AssistUserDetail userDetail = new LessonAssistResponse.AssistUserDetail();
                            userDetail.setId(assist.getUserAssist().getId());
                            userDetail.setUniId(assist.getUserAssist().getUniId());
                            userDetail.setName(assist.getUserAssist().getFirstName() + " " + assist.getUserAssist().getLastName());
                            userDetail.setEmail(assist.getUserAssist().getEmail());
                            userDetail.setEntryTime(assist.getEntryTime());
                            return userDetail;
                        }, Collectors.toList())
                ));

        return new LessonAssistResponse(
                lesson.getId(),
                lesson.getSubjectLesson(),
                lesson.getDayWeek(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                assistByDate
        );
    }

    @Transactional
    public UserLessonAssist createAssist(Long userId, Long lessonId, LocalDate entryDate, LocalTime entryTime){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("No existe el usuario"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(()-> new RuntimeException("No existe la asignatura."));

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
