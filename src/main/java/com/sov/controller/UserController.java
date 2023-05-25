package com.sov.controller;

import com.sov.data.UserData;
import com.sov.model.UserModel;
import com.sov.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserData> getUserById(@RequestParam Long id){
        UserModel user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));

        UserData userData = UserData.from(user);

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
