package com.sov.service;

import com.sov.controller.form.CreateProjectForm;
import com.sov.model.CompanyModel;
import com.sov.model.ProjectModel;
import com.sov.model.StatusModel;
import com.sov.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private CompanyService companyService;

    public ProjectModel createProject(CreateProjectForm createProjectForm) {
        CompanyModel companyModel = companyService.getCurrentCompany()
                .orElseThrow(() -> new IllegalArgumentException("Company does not exist for current user"));
        ProjectModel projectModel = new ProjectModel();
        projectModel.setName(createProjectForm.getName());
        projectModel.setFunds(createProjectForm.getFunds());
        projectModel.setDescription(createProjectForm.getDescription());
        projectModel.setStatus(StatusModel.CREATED);
        projectModel.setCompany(companyModel);
        return projectRepository.save(projectModel);
    }

    public Optional<ProjectModel> getProjectById(Long id) {
        return projectRepository.getProjectById(id);
    }

    public List<ProjectModel> getProjectsByCompanyUsername(String username) {
        return projectRepository.getProjectsByCompanyUserUsername(username);
    }

    public List<ProjectModel> getAllProjects() {
        return projectRepository.findAll();
    }

    @Resource
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Resource
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
}
