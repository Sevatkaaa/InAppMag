package com.sov.repository;

import com.sov.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
    Optional<CompanyModel> getCompanyByUserUsername(String username);
}
