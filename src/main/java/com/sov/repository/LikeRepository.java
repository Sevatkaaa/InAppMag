package com.sov.repository;

import com.sov.model.LikeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeModel, Long> {
    List<LikeModel> findAllByProjectIdAndFromId(Long projectId, Long fromId);

    List<LikeModel> findAllByProjectId(Long id);
}
