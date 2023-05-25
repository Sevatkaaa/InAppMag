package com.sov.controller;

import com.sov.controller.form.InvestFrom;
import com.sov.data.InvestorData;
import com.sov.data.ProjectData;
import com.sov.model.InvestorModel;
import com.sov.service.FileService;
import com.sov.service.InterestService;
import com.sov.service.InvestorService;
import com.sov.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/investor")
@CrossOrigin(origins = "*")
public class InvestorController {

    @Resource
    private InvestorService investorService;
    @Resource
    private InterestService interestService;
    @Resource
    private FileService fileService;
    @Resource
    private ProjectService projectService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<InvestorData> getInvestorByUserUsername(@RequestParam(required = false) String username, @RequestParam(required = false) Long id) {
        if (id != null) {
            InvestorModel investorModel = investorService.getInvestorById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Investor with id " + id + " does not exist"));
            InvestorData investorData = InvestorData.from(investorModel);
            investorData.setInvestments(investorService.getInvestorInvestments(investorModel.getId()).stream().map(i -> {
                ProjectData data = ProjectData.from(projectService.getProjectById(i.getProjectId()).orElse(null));
                data.setLogoUrl(fileService.getProjectLogo(data.getId()));
                return data;
            }).collect(Collectors.toList()));
            investorData.getInvestments().forEach(p -> p.setLogoUrl(fileService.getProjectLogo(p.getId())));
            investorData.setInterests(interestService.getInterestProjects(investorModel));
            investorData.setPhotoUrl(fileService.getInvestorPhoto(investorData.getId()));
            return ResponseEntity.ok(investorData);
        }
        InvestorModel investorModel = investorService.getInvestorByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Investor with username " + username + " does not exist"));
        InvestorData investorData = InvestorData.from(investorModel);
        investorData.setInvestments(investorService.getInvestorInvestments(investorModel.getId()).stream().map(i -> {
            ProjectData data = ProjectData.from(projectService.getProjectById(i.getProjectId()).orElse(null));
            data.setLogoUrl(fileService.getProjectLogo(data.getId()));
            return data;
        }).collect(Collectors.toList()));
        return ResponseEntity.ok(investorData);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<InvestorData> createInvestor() {
        InvestorModel investorModel = investorService.createInvestor(null);
        InvestorData investorData = InvestorData.from(investorModel);
        return ResponseEntity.ok(investorData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/projects/{projectId}")
    public ResponseEntity<InvestorData> invest(@PathVariable Long projectId, @RequestBody InvestFrom invest) {
        InvestorModel investorModel = investorService.addInvestment(projectId, invest.getMoney());
        InvestorData investorData = InvestorData.from(investorModel);
        return ResponseEntity.ok(investorData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/interests/{projectId}")
    public ResponseEntity addInterest(@PathVariable Long projectId) {
        InvestorModel investorModel = investorService.getCurrentInvestor()
                .orElseThrow(() -> new IllegalArgumentException("Investor for current user does not exist"));
        interestService.saveInterest(investorModel, projectId);
        return ResponseEntity.ok().build();
    }
}
