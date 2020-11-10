package com.example.demo.service.auth;

import com.example.demo.dto.user.LoginDto;
import com.example.demo.dto.user.RegisterDto;
import com.example.demo.model.user.Role;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRole;
import com.example.demo.repository.user.RoleRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.user.UserRoleRepository;
import com.example.demo.security.RsaUtil;
import com.example.demo.service.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@Service
public class AuthenticationService {
	
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RsaUtil rsaUtil;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, RsaUtil rsaUtil) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.rsaUtil = rsaUtil;

    }

    public boolean isUserExistByName(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    public String hasRole(HttpSession session){
        if (session.getAttribute(Message.ROLE).equals(Message.ROLE_ADMIN)){
            return Message.ROLE_ADMIN;
        }
        return Message.ROLE_USER;
    }

    public boolean login(LoginDto loginDto, HttpSession session) {

        if (!isUserExistByName(loginDto.getUsername())) {
            return false;
        }
        User user = userRepository.findUserByUsername(loginDto.getUsername());
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            UserRole userRole = userRoleRepository.findUserRoleByUserId(user.getId());
            Role role = roleRepository.findRoleById(userRole.getRole().getId());
            session.setAttribute(Message.USER_ID, user.getId());
            if (role.getRoleName().equals(Message.ROLE_ADMIN)) {
                session.setAttribute(Message.ROLE, Message.ROLE_ADMIN);
            } else {
                session.setAttribute(Message.ROLE, Message.ROLE_USER);
            }
            return true;
        }
        return false;
    }

    public boolean checkLogin(HttpSession session){
        return session.getAttribute(Message.USER_ID) != null;
    }

    public boolean register(RegisterDto registerDto){
        if (userRepository.findUserByUsername(registerDto.getUsername()) != null){
            return false;
        }
        String passwordEncrypt = passwordEncoder.encode(registerDto.getPassword());
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncrypt);
        user.setName(registerDto.getName());
        userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setRole(roleRepository.findRoleById(1));
        userRole.setUser(user);
        userRoleRepository.save(userRole);
        return true;
    }
}
