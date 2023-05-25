package com.sov.controller;

import com.sov.data.CompanyData;
import com.sov.model.CompanyModel;
import com.sov.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class CompanyController {

    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET, value = "/companies")
    public ResponseEntity<List<CompanyData>> getAllCompanies() {
        List<CompanyModel> companyModels = companyService.getAllCompanies();
        List<CompanyData> companyData = companyModels.stream()
                .map(CompanyData::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(companyData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/company")
    public ResponseEntity<CompanyData> getCompanyByUserUsername(@RequestParam String username) {
        CompanyModel companyModel = companyService.getCompanyByUserUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Company with username " + username + " does not exist"));
        CompanyData companyData = CompanyData.from(companyModel);
        return ResponseEntity.ok(companyData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/company")
    public ResponseEntity<CompanyData> createCompany() {
        CompanyModel companyModel = companyService.createCompany(null);
        CompanyData companyData = CompanyData.from(companyModel);
        return ResponseEntity.ok(companyData);
    }

    @Resource
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }
}
