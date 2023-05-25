package com.sov.controller;

import com.sov.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping(value = "/api/file")
@CrossOrigin(origins = "*")
public class FileController {

    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST, value = "/projectLogo/{projectId}")
    public ResponseEntity uploadProjectLogo(@PathVariable Long projectId, @RequestParam MultipartFile logo) {
        fileService.saveProjectLogo(projectId, logo);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/investorPhoto/{id}")
    public ResponseEntity uploadInvestorPhoto(@PathVariable Long id, @RequestParam MultipartFile logo) {
        fileService.saveInvestorPhoto(id, logo);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/projectLogo/{projectId}")
    public ResponseEntity<?> getProjectLogo(@PathVariable Long projectId) {
        String projectLogo = fileService.getProjectLogo(projectId);
        return ResponseEntity.ok(Collections.singletonMap("url", projectLogo));
    }

    @Resource
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}
