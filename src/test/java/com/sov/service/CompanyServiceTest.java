package com.sov.service;

import com.sov.model.CompanyModel;
import com.sov.model.UserModel;
import com.sov.repository.CompanyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    private static final String COMPANY_USERNAME = "company";

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private UserService userService;

    @Mock
    private CompanyModel companyModel;
    @Mock
    private UserModel userModel;

    @Before
    public void setUp() {
        when(userModel.getUsername()).thenReturn(COMPANY_USERNAME);
    }

    @Test
    public void shouldGetCompanyByUserUsername() {
        when(companyRepository.getCompanyByUserUsername(COMPANY_USERNAME))
                .thenReturn(Optional.of(companyModel));

        Optional<CompanyModel> actual = companyService.getCompanyByUserUsername(COMPANY_USERNAME);

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(companyModel);
    }

    @Test
    public void shouldGetCurrentCompany() {
        when(userService.getCurrentUser())
                .thenReturn(Optional.of(userModel));
        when(companyRepository.getCompanyByUserUsername(COMPANY_USERNAME))
                .thenReturn(Optional.of(companyModel));

        Optional<CompanyModel> actual = companyService.getCurrentCompany();

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(companyModel);
    }

    @Test
    public void shouldCreateCompany() {
        when(userService.getCurrentUser())
                .thenReturn(Optional.of(userModel));
        when(companyRepository.getCompanyByUserUsername(COMPANY_USERNAME))
                .thenReturn(Optional.empty());
        when(companyRepository.save(any(CompanyModel.class)))
                .thenReturn(companyModel);

        CompanyModel actual = companyService.createCompany(null);

        verify(companyRepository).save(any(CompanyModel.class));
        assertThat(actual).isEqualTo(companyModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateCompanyWhenItExists() {
        when(userService.getCurrentUser())
                .thenReturn(Optional.of(userModel));
        when(companyRepository.getCompanyByUserUsername(COMPANY_USERNAME))
                .thenReturn(Optional.of(companyModel));

        CompanyModel actual = companyService.createCompany(null);
    }

    @Test
    public void shouldGetAllCompanies() {
        when(companyRepository.findAll())
                .thenReturn(Collections.singletonList(companyModel));

        List<CompanyModel> actual = companyService.getAllCompanies();

        assertThat(actual).hasSize(1).contains(companyModel);
    }
}
