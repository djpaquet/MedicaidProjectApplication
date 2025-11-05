package com.medicaidProject.medicaidProject.modles.data;

import com.medicaidProject.medicaidProject.modles.Employer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EmployerDao extends JpaRepository<Employer,Long>{

    Employer findByEmail(String email);
}
