package com.proj.SegAProj.controllers;

import com.proj.SegAProj.models.Class;
import com.proj.SegAProj.services.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {

    private final ClassService classService;

    public ClassController (ClassService classService){
        this.classService = classService;
    }

    @GetMapping(path = "/{id}")
    public Class findById(@PathVariable Long id){
        return classService.findById(id);
    }

    @GetMapping
    public List<Class> findAll(){
        return classService.findAll();
    }

    @PostMapping
    public Class create(@RequestBody Class classEntity){
        return classService.create(classEntity);
    }

    @PutMapping(path = "/{id}")
    public Class update(@PathVariable Long id, @RequestBody Class classEntity){
        return classService.update(id, classEntity);
    }

    @PutMapping(path = "/{classId}/user/{userId}")
    public Class enrollUserToClass(@PathVariable Long classId,
                                   @PathVariable Long userId){

        return classService.enrollUserToClass(classId, userId);
    }

    @PutMapping(path = "/{classId}/classroom/{classroomId}")
    public Class assignClassroomToClass(@PathVariable Long classId,
                                        @PathVariable Long classroomId){
        return classService.assignClassroomToClass(classId, classroomId);
    }

    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        classService.delete(id);
    }
}
