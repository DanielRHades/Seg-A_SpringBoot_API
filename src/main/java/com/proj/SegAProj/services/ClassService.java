package com.proj.SegAProj.services;

import com.proj.SegAProj.models.Class;
import com.proj.SegAProj.repositories.ClassRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    @Autowired
    public ClassService (ClassRepository classRepository){
        this.classRepository = classRepository;
    }

    public Class findById(Long id){
        return classRepository.findById(id).orElseThrow();
    }

    public List<Class> findAll(){
        return classRepository.findAll();
    }

    @Transactional
    public Class create (Class classEntity){
        return classRepository.save(classEntity);
    }

    @Transactional
    public Class update (Long id, Class classEntity){
        var classEntityPersisted = findById(id);
        if (!Objects.equals(classEntityPersisted.getId(),id)){
            return classEntityPersisted;
        }
        BeanUtils.copyProperties(classEntity, classEntityPersisted, "id");
        return classRepository.save(classEntityPersisted);
    }

    @Transactional
    public void delete (Long id){
        classRepository.deleteById(id);
    }

}
