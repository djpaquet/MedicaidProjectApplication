package com.medicaidProject.medicaidProject.modles.data;

import com.medicaidProject.medicaidProject.modles.Address;
import com.medicaidProject.medicaidProject.modles.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressDao extends CrudRepository<Address, Long> {
    // optional: find addresses by user
    Optional<Address> findByUser(User user);

}
