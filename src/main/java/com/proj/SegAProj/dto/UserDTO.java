package com.proj.SegAProj.dto;

import com.proj.SegAProj.enums.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String idUni;
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private Set<ClassDTO> classSetHash;

    public UserDTO(Long id, String idUni, Role role, String firstName, String lastName, String email, Set<ClassDTO> classSetHash) {
        this.id = id;
        this.idUni = idUni;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.classSetHash = classSetHash;
    }
}
