package com.sov.service;

import com.sov.controller.form.RegisterForm;
import com.sov.model.RoleModel;
import com.sov.model.UserModel;
import com.sov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CurrentUserService currentUserService;

    public UserModel createUser(RegisterForm registerForm) {
        UserModel userModel = new UserModel();
        userModel.setUsername(registerForm.getUsername());
        userModel.setEmail(registerForm.getEmail());
        userModel.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        userModel.setActive(true);
        userModel.setRoles(Collections.singletonList(RoleModel.valueOf(registerForm.getRole())));
        return userRepository.save(userModel);
    }

    public Optional<UserModel> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public Optional<UserModel> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public Optional<UserModel> getCurrentUser() {
        return userRepository.getUserByUsername(getCurrentUserUsername());
    }

    private String getCurrentUserUsername() {
        return currentUserService.getCurrentUserUsername();
    }

    @Resource
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Resource
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
