package com.example.demo.repository.user;

import com.example.demo.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
    Role findRoleById(int id);
}
