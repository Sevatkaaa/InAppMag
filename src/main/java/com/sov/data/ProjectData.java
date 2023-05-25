package com.sov.data;

import com.sov.model.InterestModel;
import com.sov.model.InvestorModel;
import com.sov.model.ProjectModel;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectData {
    private Long id;
    private String name;
    private int funds;
    private String status;
    private String description;
    private String companyUsername;
    private String investorUsername;
    private Long investorId;
    private String logoUrl;
    private List<InterestData> interests;
    private LikeData likeData;
    private List<InvestmentData> investments;

    public static ProjectData from(ProjectModel projectModel) {
        if (projectModel == null) {
            return null;
        }
        ProjectData projectData = new ProjectData();
        projectData.id = projectModel.getId();
        projectData.name = projectModel.getName();
        projectData.funds = projectModel.getFunds();
        projectData.status = projectModel.getStatus().name();
        projectData.description = projectModel.getDescription();
        projectData.companyUsername = projectModel.getCompany().getUser().getUsername();
        InvestorModel investor = projectModel.getInvestor();
        projectData.investorUsername = investor == null ? null : investor.getUser().getUsername();
        projectData.investorId = investor == null ? null : investor.getId();
        return projectData;
    }

    public static ProjectData from(ProjectModel projectModel, List<InterestModel> interests) {
        ProjectData projectData = from(projectModel);
        projectData.interests = interests.stream().map(InterestData::from).collect(Collectors.toList());
        return projectData;
    }
}
