package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.LessonDTO;
import com.proj.SegAProj.dto.SubjectDTO;
import com.proj.SegAProj.models.Subject;
import com.proj.SegAProj.repositories.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    public Subject findById(Long id){
        return subjectRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No existe esta asignatura."));
    }

    public SubjectDTO findByIdWithLessons(Long id){
        return convertOneSubjectToDTOWithLessons(findById(id));
    }

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }

    public List<SubjectDTO> findAllWithLessons(){
        return convertAllSubjectsToDTOWithLessons(findAll());
    }

    @Transactional
    public Subject create (Subject subject){
        return subjectRepository.save(subject);
    }

    @Transactional
    public Subject update (Long id, Subject subject){
        var subjectPersisted = findById(id);
        if (!Objects.equals(subjectPersisted.getId(),id)){
            return subjectPersisted;
        }
        BeanUtils.copyProperties(subject, subjectPersisted, "id");
        return subjectRepository.save(subjectPersisted);
    }

    @Transactional
    public void delete (Long id){
        subjectRepository.deleteById(id);
    }

    public List<SubjectDTO> convertAllSubjectsToDTOWithLessons(List<Subject> subjectList){
        List<SubjectDTO> subjectDTOS = new ArrayList<>(subjectList.size());
        Iterator<Subject> iterator = subjectList.iterator();
        while (iterator.hasNext()){
            Subject subject = iterator.next();
            subjectDTOS.add(convertOneSubjectToDTOWithLessons(subject));
        }
        return subjectDTOS;
    }

    public SubjectDTO convertOneSubjectToDTOWithLessons(Subject subject){
        Set<LessonDTO> lessonDTOs = subject.getLessonListSubject().stream().map(
                lesson -> new LessonDTO(lesson.getId(),
                        lesson.getDayWeek(),
                        lesson.getStartTime(),
                        lesson.getEndTime(),
                        null,
                        lesson.getClassroomLesson()))
                .collect(Collectors.toSet());

        return new SubjectDTO(subject.getId(),
                subject.getNrc(),
                subject.getName(),
                lessonDTOs);

    }

}
