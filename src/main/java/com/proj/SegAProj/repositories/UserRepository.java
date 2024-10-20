package com.proj.SegAProj.repositories;

import com.proj.SegAProj.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdUni(String idUni);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM classes_users WHERE user_id = :id", nativeQuery = true)
    void deleteRowFromUserClassTable(@Param("id") Long id);

}
