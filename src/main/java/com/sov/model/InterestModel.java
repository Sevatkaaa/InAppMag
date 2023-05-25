package com.sov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "interest")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "investor_id")
    private Long investorId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "investor_name")
    private String investorName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "confirmed")
    private Boolean confirmed = false;

    public InterestModel(Long investorId, Long projectId, String investorName, String projectName) {
        this.investorId = investorId;
        this.projectId = projectId;
        this.investorName = investorName;
        this.projectName = projectName;
    }
}
