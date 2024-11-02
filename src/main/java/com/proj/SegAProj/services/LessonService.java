package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.LessonDTO;
import com.proj.SegAProj.dto.LessonRequest;
import com.proj.SegAProj.dto.SubjectDTO;
import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.models.Lesson;
import com.proj.SegAProj.models.Subject;
import com.proj.SegAProj.repositories.ClassroomRepository;
import com.proj.SegAProj.repositories.LessonRepository;
import com.proj.SegAProj.repositories.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository,
                         SubjectRepository subjectRepository,
                         ClassroomRepository classroomRepository){
        this.lessonRepository = lessonRepository;
        this.subjectRepository = subjectRepository;
        this.classroomRepository = classroomRepository;
    }

    public Lesson findById(Long id){
        return lessonRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No existe esta lección."));
    }

    public LessonDTO findByIdWithUsers(Long id){
        return convertOneLessonToDTOWithUsers(findById(id));
    }

    public List<Lesson> findAll(){
        return lessonRepository.findAll();
    }
    public List<LessonDTO> findAllWithUsers(){
        return convertAllLessonsToDTOWithUsers(findAll());
    }

    @Transactional
    public Lesson create (LessonRequest lessonRequest){
        Lesson lesson = convertToEntity(lessonRequest);
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson update (Long id, LessonRequest lessonRequest){
        var lessonPersisted = findById(id);
        Lesson lesson = convertToEntity(lessonRequest);
        if (!Objects.equals(lessonPersisted.getId(),id)){
            return lessonPersisted;
        }
        BeanUtils.copyProperties(lesson, lessonPersisted, "id",
                "userListLesson",
                "lessonAssistListLesson",
                "subjectLesson",
                "classroomLesson");
        return lessonRepository.save(lessonPersisted);
    }

    @Transactional
    public void delete (Long id){
        lessonRepository.deleteById(id);
    }

    @Transactional
    public Lesson assignSubjectToLesson(Long subjectId, Long classroomId){
        Lesson lesson = findById(subjectId);
        Subject subject = subjectRepository.findById(classroomId).orElseThrow(()->new RuntimeException("No existe la asignatura"));
        lesson.setSubjectLesson(subject);
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson unassignSubjectToLesson(Long id){
        Lesson lesson = findById(id);
        lesson.setSubjectLesson(null);
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson assignClassroomToLesson(Long lessonId, Long classroomId){
        Lesson lesson = findById(lessonId);
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(()->new RuntimeException("No existe la asignatura"));
        Iterator<Lesson> lessonIterator = classroom.getLessonListClassroom().iterator();
        while (lessonIterator.hasNext()){
            Lesson lessonPointer = lessonIterator.next();
            boolean interference = lesson.getDayWeek().equals(lessonPointer.getDayWeek()) &&
                    lesson.getStartTime().isBefore(lessonPointer.getEndTime()) &&
                    lesson.getEndTime().isAfter(lessonPointer.getStartTime());
            if (interference){
                throw new RuntimeException("Existe una asignatura en ese salon, a esa hora.");
            }
        }
        lesson.setClassroomLesson(classroom);
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson unassignClassroomToLesson(Long id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(()->new RuntimeException("No existe la asignatura."));
        lesson.setClassroomLesson(null);
        return lessonRepository.save(lesson);
    }

    public List<LessonDTO> convertAllLessonsToDTOWithUsers(List<Lesson> lessonList){
        List<LessonDTO> lessonDTOs = new ArrayList<>(lessonList.size());
        Iterator<Lesson> lessonIterator = lessonList.iterator();
        while (lessonIterator.hasNext()){
            Lesson lesson = lessonIterator.next();
            lessonDTOs.add(convertOneLessonToDTOWithUsers(lesson));
        }
        return lessonDTOs;
    }

    public LessonDTO convertOneLessonToDTOWithUsers(Lesson lesson){
        Set<UserDTO> userDTOS = lesson.getUserListLesson().stream().map(
                        user -> new UserDTO(user.getId(),
                                user.getUniId(),
                                user.getRole(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail()))
                .collect(Collectors.toSet());

        return new LessonDTO(lesson.getId(),
                lesson.getDayWeek(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getSubjectLesson(),
                lesson.getClassroomLesson(),
                userDTOS);
    }

    private Lesson convertToEntity(LessonRequest lessonRequest) {
        return new Lesson(
                lessonRequest.getDayWeek(),
                lessonRequest.getStartTime(),
                lessonRequest.getEndTime()
        );
    }
}
