package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.SubjectDTO;
import com.proj.SegAProj.dto.ClassroomDTO;
import com.proj.SegAProj.dto.ReservationDTO;
import com.proj.SegAProj.models.Classroom;
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
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository){
        this.classroomRepository = classroomRepository;
    }

    public Classroom findById(Long id){
        return classroomRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No existe este salon."));
    }

    public ClassroomDTO findByIdWithSubjects(Long id){
        return convertOneClassroomToDTOWithSubjects(findById(id));
    }

    public ClassroomDTO findByIdWithReservations(Long id){
        return convertOneClassroomToDTOWithReservations(findById(id));
    }
    public List<Classroom> findAll(){
        return classroomRepository.findAll();
    }

    public List<ClassroomDTO> findAllWithSubjects(){
        return convertAllClassroomToDTOWithSubjects(findAll());
    }

    public List<ClassroomDTO> findAllWithReservations(){
        return convertAllClassroomToDTOWithReservations(findAll());
    }

    @Transactional
    public Classroom create(Classroom classroom){
        return classroomRepository.save(classroom);
    }

    @Transactional
    public  Classroom update(Long id, Classroom classroom){
        var classroomPersisted = findById(id);
        if(!Objects.equals(classroomPersisted.getId(), id)){
            return classroomPersisted;
        }
        BeanUtils.copyProperties(classroom, classroomPersisted, "id");
        return classroomRepository.save(classroomPersisted);
    }

    @Transactional
    public void delete (Long id){
        classroomRepository.deleteById(id);
    }

    public ClassroomDTO convertOneClassroomToDTOWithSubjects(Classroom classroom){
        Set<SubjectDTO> subjectDTOS = classroom.getSubjectListClassroom().stream()
                .map(subject -> new SubjectDTO(subject.getId(),
                        subject.getNrc(),
                        subject.getName(),
                        subject.getDayWeek(),
                        subject.getStartTime(),
                        subject.getEndTime()))
                .collect(Collectors.toSet());

        return new ClassroomDTO(
                classroom.getId(),
                classroom.getName(),
                classroom.getCapacity(),
                subjectDTOS,
                null
        );
    }

    public ClassroomDTO convertOneClassroomToDTOWithReservations(Classroom classroom){
        Set<ReservationDTO> reservationDTOs = classroom.getReservationListClassroom().stream()
                .map(reservation -> new ReservationDTO(reservation.getId(),
                        reservation.getReservationDate(),
                        reservation.getStartTime(),
                        reservation.getEndTime()))
                .collect(Collectors.toSet());

        return new ClassroomDTO(
                classroom.getId(),
                classroom.getName(),
                classroom.getCapacity(),
                null,
                reservationDTOs
        );
    }

    public List<ClassroomDTO> convertAllClassroomToDTOWithSubjects(List<Classroom> classroomList){
        List<ClassroomDTO> classroomDTOList = new ArrayList<>();
        for (Classroom classroom : classroomList){
            classroomDTOList.add(convertOneClassroomToDTOWithSubjects(classroom));
        }
        return classroomDTOList;
    }

    public List<ClassroomDTO> convertAllClassroomToDTOWithReservations(List<Classroom> classroomList){
        List<ClassroomDTO> classroomDTOList = new ArrayList<>();
        for (Classroom classroom : classroomList){
            classroomDTOList.add(convertOneClassroomToDTOWithReservations(classroom));
        }
        return classroomDTOList;
    }

}
