package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.SubjectDTO;
import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.Subject;
import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.repositories.SubjectRepository;
import com.proj.SegAProj.repositories.ClassroomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository,
                          ClassroomRepository classroomRepository){
        this.subjectRepository = subjectRepository;
        this.classroomRepository = classroomRepository;
    }

    public Subject findById(Long id){
        return subjectRepository.findById(id).orElseThrow();
    }

    public SubjectDTO findByIdWithUsers(Long id){
        return convertOneSubjectToDTOWithUsers(subjectRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No existe esta asignatura.")));
    }

    public List<SubjectDTO> findAllWithUsers(){
        return convertAllSubjectsToDTOWithUsers(findAll());

    }

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }

    @Transactional
    public Subject create (Subject subjectEntity){
        subjectEntity.setClassroomSubject(null);
        return subjectRepository.save(subjectEntity);
    }

    @Transactional
    public Subject update (Long id, Subject subject){
        subject.setClassroomSubject(null);
        var subjectPersisted = findById(id);
        if (!Objects.equals(subjectPersisted.getId(),id)){
            return subjectPersisted;
        }
        BeanUtils.copyProperties(subject, subjectPersisted, "id", "classroomSubject");
        return subjectRepository.save(subjectPersisted);
    }

    @Transactional
    public void delete (Long id){
        subjectRepository.deleteById(id);
    }

    @Transactional
    public Subject assignClassroomToSubject(Long subjectId, Long classroomId){
        Subject subject = findById(subjectId);
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(()->new RuntimeException("No existe la asignatura"));
        List<Subject> subjectList = classroom.getSubjectListClassroom();
        for (Subject sub : subjectList){
            boolean interference = subject.getDayWeek().equals(sub.getDayWeek()) &&
                    subject.getStartTime().isBefore(sub.getEndTime()) &&
                    subject.getEndTime().isAfter(sub.getStartTime());
            if (interference){
                throw new RuntimeException("Existe una asignatura en ese salon, a esa hora.");
            }
        }
        subject.setClassroomSubject(classroom);
        return subjectRepository.save(subject);
    }

    @Transactional
    public Subject unassignClassroomToSubject(Long id){
        Subject subject = subjectRepository.findById(id).orElseThrow(()->new RuntimeException("No existe la asignatura."));
        subject.setClassroomSubject(null);
        return subjectRepository.save(subject);
    }

    public List<SubjectDTO> convertAllSubjectsToDTOWithUsers(List<Subject> subjectList){
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for (Subject subject : subjectList){
            subjectDTOS.add(convertOneSubjectToDTOWithUsers(subject));
        }
        return subjectDTOS;
    }

    public SubjectDTO convertOneSubjectToDTOWithUsers(Subject subject){
        Set<UserDTO> userDTOS = subject.getUserListSubject().stream().map(
                user -> new UserDTO(user.getId(),
                        user.getUniId(),
                        user.getRole(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()))
                .collect(Collectors.toSet());

        return new SubjectDTO(subject.getId(),
                subject.getNrc(),
                subject.getName(),
                subject.getDayWeek(),
                subject.getStartTime(),
                subject.getEndTime(),
                subject.getClassroomSubject(),
                userDTOS);

    }

}
