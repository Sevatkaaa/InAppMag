package com.sov.data;

import com.sov.model.CompanyModel;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CompanyData {
    private Long id;
    private UserData user;
    private List<ProjectData> projects;

    public static CompanyData from(CompanyModel companyModel) {
        CompanyData companyData = new CompanyData();
        companyData.id = companyModel.getId();
        companyData.user = UserData.from(companyModel.getUser());
        companyData.projects = companyModel.getProjects()
                .stream()
                .map(ProjectData::from)
                .collect(Collectors.toList());
        return companyData;
    }
}
