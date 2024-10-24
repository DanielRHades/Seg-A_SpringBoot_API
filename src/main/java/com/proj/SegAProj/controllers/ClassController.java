package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.ClassDTO;
import com.proj.SegAProj.models.Class;
import com.proj.SegAProj.services.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {

    private final ClassService classService;

    public ClassController (ClassService classService){
        this.classService = classService;
    }

    @Operation(summary = "Obtiene una clase por su ID.")
    @GetMapping(path = "/{id}")
    public Class findById(@PathVariable Long id){
        return classService.findById(id);
    }

    @Operation(summary = "Obtiene una clase por su ID, AGREGANDO todos los usuarios que pertenecen a la clase.")
    @GetMapping(path = "/with-users/{id}")
    public ClassDTO findByIdWithUsers(@PathVariable Long id){
        return classService.findByIdWithUsers(id);
    }

    @Operation(summary = "Obtiene todas las clases, AGREGANDO todos los usuarios que pertenecen a las clases.")
    @GetMapping(path = "/with-users")
    public List<ClassDTO> findAllWithUsers(){
        return classService.findAllWithUsers();
    }

    @Operation(summary = "Obtiene todas las clases.")
    @GetMapping
    public List<Class> findAll(){
        return classService.findAll();
    }

    @Operation(summary = "Crea una nueva clase. NOTA IMPORTANTE: " +
            "\n El campo classroomClass debe ser null en la Request, la unión de la clase con un salon se hace con su metodo especifico. " +
            "Si se agrega alguna información en el campo sera nula y no se tomara en cuenta en la Request.")
    @PostMapping
    public Class create(@RequestBody Class classEntity){return classService.create(classEntity);
    }

    @Operation(summary = "Actualiza una clase por su ID. NOTA IMPORTANTE: " +
            "\n El campo classroomClass debe ser null en la Request, la unión de la clase con un salon se hace con su metodo especifico. " +
            "Si se agrega alguna información en el campo sera nula y no se tomara en cuenta en la Request.")
    @PutMapping(path = "/{id}")
    public Class update(@PathVariable Long id, @RequestBody Class classEntity){
        return classService.update(id, classEntity);
    }

    @Operation(summary = "Put encargado de asignar un salon a una clase utilizando las ID de cada uno. " +
            "Le agrega el id_classroom a la entidad en la tabla classes basado en el principio de ManyToOne como Foreign Key.")
    @PutMapping(path = "/{classId}/classroom/{classroomId}")
    public Class assignClassroomToClass(@PathVariable Long classId,
                                        @PathVariable Long classroomId){
        return classService.assignClassroomToClass(classId, classroomId);
    }

    @Operation(summary = "Delete encargado de borrar el salon previamente asignado a una clase utilizando el ID de la clase.")
    @DeleteMapping(path = "/delete/classroom-in-class/{id}")
    public Class unassignClassroomToClass(@PathVariable Long id){
        return classService.unassignClassroomToClass(id);
    }

    @Operation(summary = "Delete encargado de borrar una clase utilizando su ID.")
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        classService.delete(id);
    }

}
