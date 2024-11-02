package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.SubjectDTO;
import com.proj.SegAProj.models.Subject;
import com.proj.SegAProj.services.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @Operation(summary = "Obtiene una asignatura por su ID.")
    @GetMapping(path = "/{id}")
    public Subject findById(@PathVariable Long id){
        return subjectService.findById(id);
    }

    @Operation(summary = "Obtiene una asignatura por su ID, AGREGANDO todos las lecciones que pertenecen a la asignatura.")
    @GetMapping(path = "/with-lessons/{id}")
    public SubjectDTO findByIdWithLessons(@PathVariable Long id){
        return subjectService.findByIdWithLessons(id);
    }

    @Operation(summary = "Obtiene todas las asignaturas, AGREGANDO todas las lecciones que pertenecen a las asignaturas.")
    @GetMapping(path = "/with-lessons")
    public List<SubjectDTO> findAllWithLessons(){
        return subjectService.findAllWithLessons();
    }

    @Operation(summary = "Obtiene todas las asignatura.")
    @GetMapping
    public List<Subject> findAll(){
        return subjectService.findAll();
    }

    @Operation(summary = "Crea una nueva asignatura.")
    @PostMapping
    public Subject create(@RequestBody Subject subject){return subjectService.create(subject);
    }

    @Operation(summary = "Actualiza una asignatura por su ID.")
    @PutMapping(path = "/{id}")
    public Subject update(@PathVariable Long id, @RequestBody Subject subject){
        return subjectService.update(id, subject);
    }

    @Operation(summary = "Delete encargado de borrar una asignatura utilizando su ID.")
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        subjectService.delete(id);
    }

}
