package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.LessonDTO;
import com.proj.SegAProj.dto.LessonRequest;
import com.proj.SegAProj.models.Lesson;
import com.proj.SegAProj.services.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService){
        this.lessonService = lessonService;
    }

    @Operation(summary = "Se encuentra una lección por la ID.")
    @GetMapping(path = "/{id}")
    public Lesson findById(@PathVariable Long id){
        return lessonService.findById(id);
    }

    @Operation(summary = "Se encuentran todas las lecciones.")
    @GetMapping
    public List<Lesson> findAll(){
        return lessonService.findAll();
    }

    @Operation(summary = "Se encuentra una lección por la ID, junto con los usuarios inscritos.")
    @GetMapping(path = "/with-users/{id}")
    public LessonDTO findByIdWithUsers(@PathVariable Long id){
        return lessonService.findByIdWithUsers(id);
    }

    @Operation(summary = "Se encuentran todas las lecciones, junto con los usuarios inscritos de todas las lecciones.")
    @GetMapping(path = "/with-users")
    public List<LessonDTO> findAllWithUsers(){
        return lessonService.findAllWithUsers();
    }

    @Operation(summary = "Crear una lección.")
    @PostMapping
    public Lesson create(@RequestBody LessonRequest lessonRequest){
        return lessonService.create(lessonRequest);
    }

    @Operation(summary = "Actualizar una lección por medio de su ID.")
    @PutMapping(path = "/{id}")
    public Lesson update(@PathVariable Long id, @RequestBody LessonRequest lessonRequest){
        return lessonService.update(id, lessonRequest);
    }

    @Operation(summary = "Put encargado de asignar una asignatura a una lección utilizando las ID de cada uno. " +
            "Le agrega el id_classroom a la entidad en la tabla classes basado en el principio de ManyToOne como Foreign Key.")
    @PutMapping(path = "/{lessonId}/subject/{subjectId}")
    public Lesson assignSubjectToLesson(@PathVariable Long lessonId,
                                          @PathVariable Long subjectId){
        return lessonService.assignSubjectToLesson(lessonId, subjectId);
    }

    @Operation(summary = "Delete encargado de borrar la asignatura previamente asignada a una lección utilizando el ID de la asignatura.")
    @DeleteMapping(path = "/delete/subject-in-lesson/{id}")
    public Lesson unassignSubjectToLesson(@PathVariable Long id){
        return lessonService.unassignSubjectToLesson(id);
    }

    @Operation(summary = "Put encargado de asignar un salon a una lección utilizando las ID de cada uno. " +
            "Le agrega el id_classroom a la entidad en la tabla classes basado en el principio de ManyToOne como Foreign Key.")
    @PutMapping(path = "/{lessonId}/classroom/{classroomId}")
    public Lesson assignClassroomToLesson(@PathVariable Long lessonId,
                                          @PathVariable Long classroomId){
        return lessonService.assignClassroomToLesson(lessonId, classroomId);
    }

    @Operation(summary = "Delete encargado de borrar el salon previamente asignado a una lección utilizando el ID de la asignatura.")
    @DeleteMapping(path = "/delete/classroom-in-lesson/{id}")
    public Lesson unassignClassroomToLesson(@PathVariable Long id){
        return lessonService.unassignClassroomToLesson(id);
    }

    @Operation(summary = "Eliminar una lección.")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        lessonService.delete(id);
    }

}
