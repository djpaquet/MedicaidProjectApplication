package com.medicaidProject.medicaidProject.modles.data;

import com.medicaidProject.medicaidProject.modles.States;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface StatesDao extends JpaRepository<States, Long>{

    States findByStateCode(String stateCode);

    States findByStateName(String stateName);
}
