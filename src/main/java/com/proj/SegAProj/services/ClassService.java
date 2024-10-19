package com.proj.SegAProj.services;

import com.proj.SegAProj.models.Class;
import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.models.User;
import com.proj.SegAProj.repositories.ClassRepository;
import com.proj.SegAProj.repositories.ClassroomRepository;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassService (ClassRepository classRepository,
                         UserRepository userRepository,
                         ClassroomRepository classroomRepository){
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
    }

    public Class findById(Long id){
        return classRepository.findById(id).orElseThrow();
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
    public Class enrollUserToClass(Long classId, Long userId){
        Class classEntity = classRepository.findById(classId).orElseThrow(()->new RuntimeException("No existe la clase."));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No existe el usuario"));
        classEntity.enrollUserToClass(user);
        return classRepository.save(classEntity);
    }

    @Transactional
    public Class assignClassroomToClass(Long classId, Long classroomId){
        Class classEntity = classRepository.findById(classId).orElseThrow(()->new RuntimeException("No existe la clase."));
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(()->new RuntimeException("No existe el usuario"));
        classEntity.setClassroomClass(classroom);
        return classRepository.save(classEntity);
    }


    @Transactional
    public void delete (Long id){
        classRepository.deleteById(id);
    }

}
