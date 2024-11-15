package com.proj.SegAProj.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class SubjectDTO implements Serializable {
    private Long id;
    private String nrc;
    private String name;
    private Set<LessonDTO> lessonSetHash;

    public SubjectDTO(Long id, String nrc, String name) {
        this.id = id;
        this.nrc = nrc;
        this.name = name;
    }

    public SubjectDTO(Long id, String nrc, String name, Set<LessonDTO> lessonSetHash) {
        this.id = id;
        this.nrc = nrc;
        this.name = name;
        this.lessonSetHash = lessonSetHash;
    }

}
