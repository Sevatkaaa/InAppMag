package com.sov.controller;

import com.sov.controller.form.CreateProjectForm;
import com.sov.data.InvestmentData;
import com.sov.data.LikeData;
import com.sov.data.ProjectData;
import com.sov.model.InterestModel;
import com.sov.model.ProjectModel;
import com.sov.service.*;
import javafx.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @Resource
    private InterestService interestService;

    @Resource
    private FileService fileService;

    @Resource
    private LikeService likeService;

    @Resource
    private InvestorService investorService;

    @RequestMapping(method = RequestMethod.GET, value = "/projects")
    public ResponseEntity<List<ProjectData>> getAllProjects() {
        List<ProjectModel> projectModels = projectService.getAllProjects();
        List<ProjectData> projects = projectModels.stream()
                .map(ProjectData::from)
                .collect(Collectors.toList());
        projects.forEach(p -> p.setLogoUrl(fileService.getProjectLogo(p.getId())));
        return ResponseEntity.ok(projects);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/projects/{id}")
    public ResponseEntity<ProjectData> getProject(@PathVariable Long id) {
        ProjectModel projectModel = projectService.getProjectById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project with id " + id + " does not exist"));;
        List<InterestModel> interests = interestService.getInterestsForProject(id);
        ProjectData project = ProjectData.from(projectModel, interests);
        project.getInterests().stream().forEach(i -> i.setInvestorPhoto(fileService.getInvestorPhoto(i.getInvestorId())));
        project.setLogoUrl(fileService.getProjectLogo(id));
        project.setLikeData(likeService.getProjectLikes(id));
        project.setInvestments(investorService.getProjectInvestments(id).stream().map(InvestmentData::from).collect(Collectors.toList()));
        return ResponseEntity.ok(project);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/companies/{username}/projects")
    public ResponseEntity<List<ProjectData>> getProjectsForCompanyWithUsername(@PathVariable String username) {
        List<ProjectModel> projectModels = projectService.getProjectsByCompanyUsername(username);
        List<ProjectData> projects = projectModels.stream()
                .map(ProjectData::from)
                .collect(Collectors.toList());
        projects.forEach(p -> p.setLogoUrl(fileService.getProjectLogo(p.getId())));
        return ResponseEntity.ok(projects);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/project")
    public ResponseEntity<ProjectData> createProject(@RequestBody CreateProjectForm createProjectForm) {
        ProjectModel projectModel = projectService.createProject(createProjectForm);
        ProjectData projectData = ProjectData.from(projectModel);
        projectData.setLogoUrl(fileService.getProjectLogo(projectData.getId()));
        return ResponseEntity.ok(projectData);
    }

    @Resource
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
}
