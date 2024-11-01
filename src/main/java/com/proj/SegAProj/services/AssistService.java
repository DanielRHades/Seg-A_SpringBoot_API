package com.proj.SegAProj.services;

import com.proj.SegAProj.models.Subject;
import com.proj.SegAProj.models.User;
import com.proj.SegAProj.models.UserSubjectAssist;
import com.proj.SegAProj.models.UserSubjectAssistKey;
import com.proj.SegAProj.repositories.AssistRepository;
import com.proj.SegAProj.repositories.SubjectRepository;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AssistService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final AssistRepository assistRepository;

    @Autowired
    public  AssistService (UserRepository userRepository, SubjectRepository subjectRepository, AssistRepository assistRepository){
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.assistRepository = assistRepository;
    }

    @Transactional
    public UserSubjectAssist createAssist(Long userId, Long subjectId, LocalDate entryDate, LocalTime entryTime){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No existe el usuario"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()-> new RuntimeException("No existe la asignatura."));

        UserSubjectAssistKey assistKey = new UserSubjectAssistKey();
        assistKey.setUserId(userId);
        assistKey.setSubjectId(subjectId);
        assistKey.setEntryDate(entryDate);

        UserSubjectAssist assist = new UserSubjectAssist();
        assist.setId(assistKey);
        assist.setUserAssist(user);
        assist.setSubjectAssist(subject);
        assist.setEntryTime(entryTime);

        user.getSubjectAssistListUser().add(assist);
        subject.getSubjectAssistListSubject().add(assist);
        return assistRepository.save(assist);
    }

//    @Transactional
//    public User findUserByIdWithAssists(){
//
//    }

//    @Transactional
//    public Subject findSubjectByIdWithAssists(){
//
//    }

}
