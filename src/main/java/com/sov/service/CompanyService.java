package com.sov.service;

import com.sov.model.CompanyModel;
import com.sov.model.UserModel;
import com.sov.repository.CompanyRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;
    private UserService userService;

    public Optional<CompanyModel> getCompanyByUserUsername(String username) {
        return companyRepository.getCompanyByUserUsername(username);
    }

    public Optional<CompanyModel> getCurrentCompany() {
        UserModel userModel = userService.getCurrentUser()
                .orElseThrow(() -> new UsernameNotFoundException("User is not logged in"));
        return companyRepository.getCompanyByUserUsername(userModel.getUsername());
    }

    public CompanyModel createCompany(String username) {
        UserModel userModel;
        if (username == null) {
            userModel = userService.getCurrentUser()
                    .orElseThrow(() -> new UsernameNotFoundException("User is not logged in"));
            username = userModel.getUsername();
        } else {
            userModel = userService.getUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        }
        String finalUsername = username;
        companyRepository.getCompanyByUserUsername(username)
                .ifPresent(companyModel -> {
                    throw new IllegalArgumentException("Company with username " + finalUsername + " already exists");
                });

        CompanyModel companyModel = new CompanyModel();
        companyModel.setUser(userModel);
        companyModel.setProjects(Collections.emptyList());
        return companyRepository.save(companyModel);
    }

    public List<CompanyModel> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Resource
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
