package com.example.demo.repository.conf;

import com.example.demo.model.conf.EmailConfig;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailConfigRepository extends JpaRepository<EmailConfig, Integer> {
    
    @Query(nativeQuery =true,value = "SELECT * FROM EmailConfig as e, User as u WHERE u.email NOT IN (:emails)")   // 3. Spring JPA In cause using native query
    List<User> findByEmailList(@Param("emails") List<String> emails);

}
