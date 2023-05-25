package com.sov.service;

import com.sov.controller.AuthenticationController;
import com.sov.controller.LikeController;
import com.sov.controller.UserController;
import com.sov.controller.form.LikeForm;
import com.sov.controller.form.RegisterForm;
import com.sov.model.LikeModel;
import com.sov.model.UserModel;
import com.sov.repository.CompanyRepository;
import com.sov.repository.LikeRepository;
import com.sov.repository.UserRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LikeServiceIntegrationTest {

    @MockBean
    private CurrentUserService currentUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeController likeController;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Test
    @Order(1)
    public void createUserTest() {
        // Given
        RegisterForm registerForm = new RegisterForm("testUser", "testPass", "testEmail", "COMPANY");
        when(currentUserService.getCurrentUserUsername()).thenReturn("testUser");

        // When
        userService.createUser(registerForm);
        companyService.createCompany(null);

        // Then
        Optional<UserModel> testUser = userService.getUserByUsername("testUser");
        assertThat(testUser).isPresent();
        assertThat(testUser.get().getUsername()).isEqualTo("testUser");
    }

    @Test
    @Order(2)
    public void likeFromExistingUserTest() {
        // Given
        UserModel user = userRepository.getUserByUsername("testUser").get();
        when(currentUserService.getCurrentUserUsername()).thenReturn("testUser");

        Long fromId = companyService.getCompanyByUserUsername(user.getUsername()).orElse(null).getId();


        List<LikeModel> likesBefore = likeRepository.findAllByProjectId(1L);

        // When
        LikeForm likeForm = new LikeForm(1L, true);
        likeController.like(likeForm);

        // Then
        List<LikeModel> likeAfter = likeRepository.findAllByProjectId(1L);
        assertThat(likeAfter.size()).isEqualTo(likesBefore.size() + 1);

        likeRepository.deleteAll();
        companyRepository.deleteAll();
        userRepository.deleteAll();
    }
}
