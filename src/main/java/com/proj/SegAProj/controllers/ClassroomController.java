package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.ClassroomDTO;
import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.services.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/classroom")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService){
        this.classroomService = classroomService;
    }

    @Operation(summary = "Obtiene un salon por su ID.")
    @GetMapping(path = "/{id}")
    public Classroom findById (@PathVariable Long id){
        return classroomService.findById(id);
    }

    @Operation(summary = "Obtiene un salon por su ID, agregando las lecciones dictadas en el salon.")
    @GetMapping(path = "/with-lessons/{id}")
    public ClassroomDTO findByIdWithLessons(@PathVariable Long id){
        return classroomService.findByIdWithLessons(id);
    }

    @Operation(summary = "Obtiene un salon por su ID, agregando las reservas programadas en el salon.")
    @GetMapping(path = "/with-reservations/{id}")
    public ClassroomDTO findByIdWithReservations(@PathVariable Long id){
        return classroomService.findByIdWithReservations(id);
    }

    @Operation(summary = "Obtiene todos los salones.")
    @GetMapping
    public List<Classroom> findAll(){
        return  classroomService.findAll();
    }

    @Operation(summary = "Obtiene todos los salones, agregando las lecciones dictadas en los salones.")
    @GetMapping(path = "/with-subjects")
    public List<ClassroomDTO> findAllWithLessons(){
        return classroomService.findAllWithLessons();
    }

    @Operation(summary = "Obtiene todos los salones, agregando las reservas programadas del salon.")
    @GetMapping(path = "/with-reservations")
    public List<ClassroomDTO> findAllWithReservations(){
        return classroomService.findAllWithReservations();
    }

    @Operation(summary = "Crea un salon nuevo.")
    @PostMapping
    public Classroom create(@RequestBody Classroom classroom){
        return  classroomService.create(classroom);
    }

    @Operation(summary = "Actuliza un salon utilizando su ID.")
    @PutMapping(path = "/{id}")
    public Classroom update(@PathVariable Long id, @RequestBody Classroom classroom){
        return classroomService.update(id, classroom);
    }

    @Operation(summary = "Elimina un salon utilizando su ID.")
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        classroomService.delete(id);
    }

}
