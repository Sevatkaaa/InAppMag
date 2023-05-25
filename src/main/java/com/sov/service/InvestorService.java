package com.sov.service;

import com.sov.model.*;
import com.sov.repository.InvestmentRepository;
import com.sov.repository.InvestorRepository;
import com.sov.repository.ProjectRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {

    private InvestorRepository investorRepository;
    private ProjectRepository projectRepository;
    private UserService userService;
    private ProjectService projectService;
    @Resource
    private InvestmentRepository investmentRepository;

    public Optional<InvestorModel> getInvestorByUsername(String username) {
        return investorRepository.getInvestorByUserUsername(username);
    }

    public Optional<InvestorModel> getInvestorById(Long id) {
        return investorRepository.findById(id);
    }

    public Optional<InvestorModel> getCurrentInvestor() {
        UserModel userModel = userService.getCurrentUser()
                .orElseThrow(() -> new UsernameNotFoundException("User is not logged in"));
        return investorRepository.getInvestorByUserUsername(userModel.getUsername());
    }

    public InvestorModel createInvestor(String username) {
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
        investorRepository.getInvestorByUserUsername(username)
                .ifPresent(companyModel -> {
                    throw new IllegalArgumentException("Investor with username " + finalUsername + " already exists");
                });
        InvestorModel investorModel = new InvestorModel();
        investorModel.setUser(userModel);
        investorModel.setInvestments(Collections.emptyList());
        return investorRepository.save(investorModel);
    }

    @Transactional
    public InvestorModel addInvestment(Long projectId, Long money) {
        InvestorModel currentInvestor = getCurrentInvestor()
                .orElseThrow(() -> new IllegalArgumentException("Investor for current user does not exist"));
        ProjectModel project = projectService.getProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with id " + projectId + " does not exist"));
        List<ProjectModel> investments = currentInvestor.getInvestments();
        if (project.getStatus() == StatusModel.INVESTED) {
            throw new IllegalArgumentException("Project with id " + projectId + " is already invested");
        }
        investmentRepository.save(new InvestmentModel(currentInvestor.getId(), projectId, money));
        List<InvestmentModel> projectInvestments = investmentRepository.findAllByProjectId(projectId);
        long totalMoneyInvested = projectInvestments.stream().mapToLong(InvestmentModel::getMoney).sum();
        if (totalMoneyInvested >= project.getFunds()) {
            project.setStatus(StatusModel.INVESTED);
            projectRepository.save(project);
        }
        investments.add(project);
        return investorRepository.save(currentInvestor);
    }

    public List<InvestmentModel> getProjectInvestments(Long projectId) {
        return investmentRepository.findAllByProjectId(projectId);
    }

    public List<InvestmentModel> getInvestorInvestments(Long investorId) {
        return investmentRepository.findAllByInvestorId(investorId);
    }

    @Resource
    public void setInvestorRepository(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    @Resource
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Resource
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
}
