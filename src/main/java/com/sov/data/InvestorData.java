package com.sov.data;

import com.sov.model.InvestorModel;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class InvestorData {
    private Long id;
    private UserData user;
    private String photoUrl;
    private String description;
    private List<ProjectData> investments;
    private List<ProjectData> interests;

    public static InvestorData from(InvestorModel investorModel) {
        if (investorModel == null) {
            return null;
        }
        InvestorData investorData = new InvestorData();
        investorData.id = investorModel.getId();
        investorData.description = investorModel.getDescription();
        investorData.user = UserData.from(investorModel.getUser());
        return investorData;
    }
}
