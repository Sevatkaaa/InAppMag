package com.sov.data;

import com.sov.model.InterestModel;
import com.sov.model.RoleModel;
import com.sov.model.UserModel;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class InterestData {
    private Long id;
    private Long investorId;
    private Long projectId;
    private String investorName;
    private String investorPhoto;
    private String projectName;
    private boolean confirmed;
    private ProjectData projectData;

    public static InterestData from(InterestModel interestModel) {
        InterestData interestData = new InterestData();
        interestData.setId(interestModel.getId());
        interestData.setInvestorId(interestModel.getInvestorId());
        interestData.setProjectId(interestModel.getProjectId());
        interestData.setInvestorName(interestModel.getInvestorName());
        interestData.setConfirmed(interestModel.getConfirmed() != null && interestModel.getConfirmed());
        interestData.setProjectName(interestModel.getProjectName());
        return interestData;
    }
}
