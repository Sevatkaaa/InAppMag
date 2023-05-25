package com.sov.controller;

import com.sov.data.InterestData;
import com.sov.data.ProjectData;
import com.sov.model.InterestModel;
import com.sov.model.InvestorModel;
import com.sov.service.FileService;
import com.sov.service.InterestService;
import com.sov.service.InvestorService;
import com.sov.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/interests")
@CrossOrigin(origins = "*")
public class InterestController {

    @Resource
    private ProjectService projectService;

    @Resource
    private InterestService interestService;

    @Resource
    private InvestorService investorService;

    @Resource
    private FileService fileService;

    @RequestMapping(method = RequestMethod.GET, value = "/investor")
    public ResponseEntity<List<InterestData>> getInterestsForInvestor(@RequestParam String username, @RequestParam(required = false) boolean full) {
        InvestorModel investorModel = investorService.getInvestorByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Investor with username " + username + " does not exist"));
        List<InterestModel> interests = interestService.getInterestsForInvestor(investorModel.getId());
        List<InterestData> result = interests.stream().map(InterestData::from).collect(Collectors.toList());
        if (full) {
            result.forEach(i -> {
                i.setProjectData(ProjectData.from(projectService.getProjectById(i.getProjectId()).orElse(null)));
                i.getProjectData().setLogoUrl(fileService.getProjectLogo(i.getProjectId()));
            });
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    public ResponseEntity confirm(@PathVariable Long id) {
        interestService.confirmInterest(id);
        return ResponseEntity.ok().build();
    }
}
