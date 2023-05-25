package com.sov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "from_id")
    private Long fromId;

    @Column(name = "positive")
    private boolean positive;

    public LikeModel(Long projectId, Long fromId, boolean positive) {
        this.projectId = projectId;
        this.fromId = fromId;
        this.positive = positive;
    }
}
