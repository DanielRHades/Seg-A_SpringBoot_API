package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.AssistRequest;
import com.proj.SegAProj.models.UserLessonAssist;
import com.proj.SegAProj.services.AssistService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/assist")
public class AssistController {

    private final AssistService assistService;

    @Autowired
    public AssistController (AssistService assistService){
        this.assistService = assistService;
    }

    @PostMapping
    @Operation(summary = "Se hace registro de asistencia de un estudiante, en una asignatura, con fecha y hora.")
    public UserLessonAssist create(@RequestBody AssistRequest assist){
        return assistService.createAssist(
                assist.getUserId(),
                assist.getLessonId(),
                assist.getEntryDate(),
                assist.getEntryTime());
    }

}
