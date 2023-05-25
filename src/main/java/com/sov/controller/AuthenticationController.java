package com.sov.controller;

import com.sov.controller.form.RegisterForm;
import com.sov.data.UserData;
import com.sov.model.RoleModel;
import com.sov.model.UserModel;
import com.sov.controller.form.LoginForm;
import com.sov.security.jwt.JwtTokenProvider;
import com.sov.service.CompanyService;
import com.sov.service.InvestorService;
import com.sov.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private static final String INVESTOR = "INVESTOR";
    private static final String COMPANY = "COMPANY";

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    private InvestorService investorService;

    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        String username = loginForm.getUsername();
        UserModel user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, loginForm.getPassword()));

        String token = jwtTokenProvider.createToken(user);

        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("roles", user.getRoles().stream().map(Enum::name).collect(Collectors.toList()));
        response.put("token", token);
        response.put("id", user.getRoles().contains(RoleModel.INVESTOR) ?
                investorService.getInvestorByUsername(username).orElse(null).getId() :
                companyService.getCompanyByUserUsername(username).orElse(null).getId());

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<UserData> register(@RequestBody RegisterForm registerForm) {
        String username = registerForm.getUsername();

        userService.getUserByUsername(username).ifPresent(user -> {
            throw new IllegalArgumentException("User already exists");
        });

        UserModel userModel = userService.createUser(registerForm);
        if (INVESTOR.equals(registerForm.getRole())) {
            investorService.createInvestor(username);
        } else if (COMPANY.equals(registerForm.getRole())) {
            companyService.createCompany(username);
        }

        UserData userData = UserData.from(userModel);

        return ResponseEntity.ok(userData);
    }

    @Resource
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Resource
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Resource
    public void setInvestorService(InvestorService investorService) {
        this.investorService = investorService;
    }

    @Resource
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }
}
