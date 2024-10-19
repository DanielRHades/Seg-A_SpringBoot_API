package com.proj.SegAProj.controllers;

import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classroom")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService){
        this.classroomService = classroomService;
    }

    @GetMapping(path = "/{id}")
    public Classroom findById (@PathVariable Long id){
        return classroomService.findbyId(id);
    }

    @GetMapping
    public List<Classroom> findAll(){
        return  classroomService.findAll();
    }

    @PostMapping
    public Classroom create(@RequestBody Classroom classroom){
        return  classroomService.create(classroom);
    }

    @PutMapping(path = "/{id}")
    public Classroom update(@PathVariable Long id, @RequestBody Classroom classroom){
        return classroomService.update(id, classroom);
    }

    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        classroomService.delete(id);
    }

}
