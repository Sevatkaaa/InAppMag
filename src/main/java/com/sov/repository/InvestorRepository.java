package com.sov.repository;

import com.sov.model.InvestorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<InvestorModel, Long> {
    Optional<InvestorModel> getInvestorByUserUsername(String username);
}
