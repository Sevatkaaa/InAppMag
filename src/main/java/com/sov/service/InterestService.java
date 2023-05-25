package com.sov.service;

import com.sov.data.ProjectData;
import com.sov.model.InterestModel;
import com.sov.model.InvestorModel;
import com.sov.model.ProjectModel;
import com.sov.repository.InterestRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterestService {

    @Resource
    private ProjectService projectService;

    @Resource
    private MessageService messageService;

    @Resource
    private InterestRepository interestRepository;

    @Resource
    private FileService fileService;

    public InterestModel getInterestById(Long id) {
        return interestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not exist"));
    }

    public List<InterestModel> getInterestsForProject(Long projectId) {
        return interestRepository.findAllByProjectId(projectId);
    }

    public List<InterestModel> getInterestsForInvestor(Long investorId) {
        return interestRepository.findAllByInvestorId(investorId);
    }

    public void saveInterest(InvestorModel investor, Long projectId) {
        if (!interestRepository.findAllByInvestorIdAndProjectId(investor.getId(), projectId).isEmpty()) {
            throw new IllegalArgumentException("Interest already exists");
        }
        ProjectModel project = projectService.getProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project does not exist"));
        InterestModel interest = interestRepository.save(new InterestModel(investor.getId(), project.getId(), investor.getUser().getUsername(), project.getName()));
        messageService.addMessage(interest.getId(), investor.getId(), "Hello, I am interested in your project!");
    }

    public List<ProjectData> getInterestProjects(InvestorModel investorModel) {
        List<ProjectData> interests = getInterestsForInvestor(investorModel.getId()).stream().map(i -> ProjectData.from(projectService.getProjectById(i.getProjectId()).orElse(null))).collect(Collectors.toList());
        interests.stream().forEach(i -> i.setLogoUrl(fileService.getProjectLogo(i.getId())));
        return interests;
    }

    public void confirmInterest(Long id) {
        InterestModel interestModel = interestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
        interestModel.setConfirmed(true);
        interestRepository.save(interestModel);
    }
}
