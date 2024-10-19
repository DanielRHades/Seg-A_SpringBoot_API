package com.proj.SegAProj.repositories;

import com.proj.SegAProj.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdUni(String idUni);

}
