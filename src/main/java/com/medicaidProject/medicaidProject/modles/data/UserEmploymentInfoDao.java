package com.medicaidProject.medicaidProject.modles.data;
import com.medicaidProject.medicaidProject.modles.UserEmploymentInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEmploymentInfoDao extends CrudRepository<UserEmploymentInfo,Long> {

    UserEmploymentInfo findByUserId(Long userId);

}
