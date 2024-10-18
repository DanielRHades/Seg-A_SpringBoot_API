package com.proj.SegAProj.controllers;

import com.proj.SegAProj.models.User;
import com.proj.SegAProj.services.UserService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public User findById(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }

    @PostMapping
    public User create(@RequestBody User user){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        String passwordHash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(passwordHash);
        return userService.create(user);
    }
}
