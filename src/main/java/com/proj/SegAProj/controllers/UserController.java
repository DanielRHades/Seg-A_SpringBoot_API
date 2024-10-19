package com.proj.SegAProj.controllers;

import com.proj.SegAProj.models.User;
import com.proj.SegAProj.services.UserService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(path = "/id/{id}")
    public User findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @GetMapping(path = "/idUni/{idUni}")
    public User findByIdUni(@PathVariable String idUni){
        return userService.findByIdUni(idUni);
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        String passwordHash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(passwordHash);
        return userService.create(user);
    }

    @PutMapping(path = "/{id}")
    public User update(@RequestBody User user, @PathVariable Long id){
         return userService.update(id, user);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
