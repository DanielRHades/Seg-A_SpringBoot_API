package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.SubjectDTO;
import com.proj.SegAProj.models.Subject;
import com.proj.SegAProj.services.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
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

    @Operation(summary = "Obtiene una asignatura por su ID, AGREGANDO todos los usuarios que pertenecen a la asignatura.")
    @GetMapping(path = "/with-users/{id}")
    public SubjectDTO findByIdWithUsers(@PathVariable Long id){
        return subjectService.findByIdWithUsers(id);
    }

    @Operation(summary = "Obtiene todas las asignaturas, AGREGANDO todos los usuarios que pertenecen a las asignaturas.")
    @GetMapping(path = "/with-users")
    public List<SubjectDTO> findAllWithUsers(){
        return subjectService.findAllWithUsers();
    }

    @Operation(summary = "Obtiene todas las asignatura.")
    @GetMapping
    public List<Subject> findAll(){
        return subjectService.findAll();
    }

    @Operation(summary = "Crea una nueva asignatura. NOTA IMPORTANTE: " +
            "\n El campo classroomClass debe ser null en la Request, la unión de la asignatura con un salon se hace con su metodo especifico. " +
            "Si se agrega alguna información en el campo sera nula y no se tomara en cuenta en la Request.")
    @PostMapping
    public Subject create(@RequestBody Subject subject){return subjectService.create(subject);
    }

    @Operation(summary = "Actualiza una asignatura por su ID. NOTA IMPORTANTE: " +
            "\n El campo classroomClass debe ser null en la Request, la unión de la asignatura con un salon se hace con su metodo especifico. " +
            "Si se agrega alguna información en el campo sera nula y no se tomara en cuenta en la Request.")
    @PutMapping(path = "/{id}")
    public Subject update(@PathVariable Long id, @RequestBody Subject subject){
        return subjectService.update(id, subject);
    }

    @Operation(summary = "Put encargado de asignar un salon a una asignatura utilizando las ID de cada uno. " +
            "Le agrega el id_classroom a la entidad en la tabla classes basado en el principio de ManyToOne como Foreign Key.")
    @PutMapping(path = "/{subjectId}/classroom/{classroomId}")
    public Subject assignClassroomToSubject(@PathVariable Long subjectId,
                                          @PathVariable Long classroomId){
        return subjectService.assignClassroomToSubject(subjectId, classroomId);
    }

    @Operation(summary = "Delete encargado de borrar el salon previamente asignado a una asignatura utilizando el ID de la asignatura.")
    @DeleteMapping(path = "/delete/classroom-in-subject/{id}")
    public Subject unassignClassroomToSubject(@PathVariable Long id){
        return subjectService.unassignClassroomToSubject(id);
    }

    @Operation(summary = "Delete encargado de borrar una asignatura utilizando su ID.")
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        subjectService.delete(id);
    }

}
