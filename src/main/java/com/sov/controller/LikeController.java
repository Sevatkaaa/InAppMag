package com.sov.controller;

import com.sov.controller.form.LikeForm;
import com.sov.data.LikeData;
import com.sov.model.ProjectModel;
import com.sov.service.LikeService;
import com.sov.service.ProjectService;
import javafx.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/likes")
@CrossOrigin(origins = "*")
public class LikeController {

    @Resource
    private LikeService likeService;

    @Resource
    private ProjectService projectService;

    @RequestMapping(method = RequestMethod.POST, value = "/like")
    public ResponseEntity like(@RequestBody LikeForm likeForm) {
        likeService.like(likeForm.getProjectId(), likeForm.isPositive());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/unlike")
    public ResponseEntity unlike(@RequestBody LikeForm likeForm) {
        likeService.unlike(likeForm.getProjectId(), likeForm.isPositive());
        return ResponseEntity.ok().build();
    }

}
