package com.sov.repository;

import com.sov.model.InvestmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentModel, Long> {
    List<InvestmentModel> findAllByProjectId(Long projectId);
    List<InvestmentModel> findAllByInvestorId(Long investorId);
}
