package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.ClassDTO;
import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.Class;
import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.repositories.ClassRepository;
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
public class ClassService {

    private final ClassRepository classRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassService (ClassRepository classRepository,
                         ClassroomRepository classroomRepository){
        this.classRepository = classRepository;
        this.classroomRepository = classroomRepository;
    }

    public Class findById(Long id){
        return classRepository.findById(id).orElseThrow();
    }

    public ClassDTO findByIdWithUsers(Long id){
        return convertOneClassToDTOWithUsers(classRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No existe esta clase.")));
    }

    public List<ClassDTO> findAllWithUsers(){
        return convertAllClassesToDTOWithUsers(findAll());

    }

    public List<Class> findAll(){
        return classRepository.findAll();
    }

    @Transactional
    public Class create (Class classEntity){
        classEntity.setClassroomClass(null);
        return classRepository.save(classEntity);
    }

    @Transactional
    public Class update (Long id, Class classEntity){
        classEntity.setClassroomClass(null);
        var classEntityPersisted = findById(id);
        if (!Objects.equals(classEntityPersisted.getId(),id)){
            return classEntityPersisted;
        }
        BeanUtils.copyProperties(classEntity, classEntityPersisted, "id", "classroomClass");
        return classRepository.save(classEntityPersisted);
    }

    @Transactional
    public void delete (Long id){
        classRepository.deleteById(id);
    }

    @Transactional
    public Class assignClassroomToClass(Long classId, Long classroomId){
        Class classEntity = findById(classId);
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(()->new RuntimeException("No existe el usuario"));
        List<Class> classList = classroom.getClassListClassroom();
        for (Class cls : classList){
            boolean interference = classEntity.getDayWeek().equals(cls.getDayWeek()) &&
                    classEntity.getStartTime().isBefore(cls.getEndTime()) &&
                    classEntity.getEndTime().isAfter(cls.getStartTime());
            if (interference){
                throw new RuntimeException("Existe una clase en ese salon, a esa hora.");
            }
        }
        classEntity.setClassroomClass(classroom);
        return classRepository.save(classEntity);
    }

    @Transactional
    public Class unassignClassroomToClass(Long id){
        Class classEntity = classRepository.findById(id).orElseThrow(()->new RuntimeException("No existe la clase."));
        classEntity.setClassroomClass(null);
        return classRepository.save(classEntity);
    }

    public List<ClassDTO> convertAllClassesToDTOWithUsers(List<Class> classList){
        List<ClassDTO> classDTOS = new ArrayList<>();
        for (Class classEntity : classList){
            classDTOS.add(convertOneClassToDTOWithUsers(classEntity));
        }
        return classDTOS;
    }

    public ClassDTO convertOneClassToDTOWithUsers(Class classEntity){
        Set<UserDTO> userDTOS = classEntity.getUserListClass().stream().map(
                user -> new UserDTO(user.getId(),
                        user.getIdUni(),
                        user.getRole(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()))
                .collect(Collectors.toSet());

        return new ClassDTO(classEntity.getId(),
                classEntity.getName(),
                classEntity.getDayWeek(),
                classEntity.getStartTime(),
                classEntity.getEndTime(),
                classEntity.getClassroomClass(),
                userDTOS);

    }

}
