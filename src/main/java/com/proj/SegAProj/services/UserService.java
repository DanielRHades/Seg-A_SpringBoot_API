package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.ClassDTO;
import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.Class;
import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.models.User;
import com.proj.SegAProj.repositories.ClassRepository;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    @Autowired
    public UserService (UserRepository userRepository, ClassRepository classRepository){
        this.userRepository = userRepository;
        this.classRepository = classRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public UserDTO findByIdDTO(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario"));
        return convertOneUserToDTO(user);
    }

    public List<UserDTO> findAllDTO() {
        List<User> user = userRepository.findAll();
        return convertAllUserToDTO(user);
    }

    public User findByIdUni (String idUni){
        return userRepository.findByIdUni(idUni).orElseThrow();
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

    @Transactional
    public User enrollClassToUser(Long userId, Long classId){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No existe el usuario"));
        Class classEntity = classRepository.findById(classId).orElseThrow(()->new RuntimeException("No existe la clase."));
        user.getClassListUser().add(classEntity);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteEnrollClassToUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("No existe la clase."));
        userRepository.deleteRowFromUserClassTable(user.getId());
    }

    @Transactional
    public List<UserDTO> convertAllUserToDTO(List<User> users){
        List<UserDTO> userList = new ArrayList<>();
        for (User user : users){
             userList.add(convertOneUserToDTO(user));
        }
        return userList;
    }
    @Transactional
    public UserDTO convertOneUserToDTO(User user) {
        Set<ClassDTO> classDTOs = user.getClassListUser().stream()
                .map(classEntity -> new ClassDTO(classEntity.getId(),
                        classEntity.getName(),
                        classEntity.getDayWeek(),
                        classEntity.getStartTime(),
                        classEntity.getEndTime()))
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getIdUni(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                classDTOs
        );
    }
}
