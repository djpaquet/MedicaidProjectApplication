package com.medicaidProject.medicaidProject.modles.data;

import com.medicaidProject.medicaidProject.modles.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

