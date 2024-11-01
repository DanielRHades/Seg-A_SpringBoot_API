package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.AssistRequestDTO;
import com.proj.SegAProj.models.UserSubjectAssist;
import com.proj.SegAProj.services.AssistService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assist")
public class AssistController {

    private final AssistService assistService;

    @Autowired
    public AssistController (AssistService assistService){
        this.assistService = assistService;
    }

    @PostMapping
    @Operation(summary = "Se hace registro de asistencia de un estudiante, en una asignatura, con fecha y hora.")
    public UserSubjectAssist create(@RequestBody AssistRequestDTO assist){
        return assistService.createAssist(
                assist.getUserId(),
                assist.getSubjectId(),
                assist.getEntryDate(),
                assist.getEntryTime());
    }

}
