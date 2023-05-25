package com.sov.repository;

import com.sov.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {
    List<ProjectModel> getProjectsByCompanyUserUsername(String username);
    Optional<ProjectModel> getProjectById(Long id);
}
