package com.proj.SegAProj.services;

import com.proj.SegAProj.models.User;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public User create (User user){
        return userRepository.save(user);
    }

    @Transactional
    public User update (Long id, User user){
        var userPersisted = findById(id);
        if (!Objects.equals(userPersisted.getId(), id)) {
            return userPersisted;
        }
        BeanUtils.copyProperties(user, userPersisted, "id");
        return userRepository.save(userPersisted);
    }

    @Transactional
    public void delete (Long id){
        userRepository.deleteById(id);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

}
