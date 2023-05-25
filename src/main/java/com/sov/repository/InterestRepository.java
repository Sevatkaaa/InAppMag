package com.sov.repository;

import com.sov.model.InterestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<InterestModel, Long> {
    List<InterestModel> findAllByProjectId(Long projectId);
    List<InterestModel> findAllByInvestorId(Long investorId);
    List<InterestModel> findAllByInvestorIdAndProjectId(Long investorId, Long projectId);
}
