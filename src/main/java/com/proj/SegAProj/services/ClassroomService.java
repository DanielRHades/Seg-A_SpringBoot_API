package com.proj.SegAProj.services;

import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.repositories.ClassroomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    public List<Classroom> findAll(){
        return classroomRepository.findAll();
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

}
