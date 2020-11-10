package com.example.demo.repository.user;

import com.example.demo.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	
    UserRole findUserRoleByUserId(String userId);

    List<UserRole> findUserRoleByRoleId(int roleId);

    UserRole findUserRoleByUserIdAndRoleId(int userId, int roleId);
}
