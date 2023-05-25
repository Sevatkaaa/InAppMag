package com.sov.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "project")
@Data
public class ProjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int funds;

    @Enumerated(EnumType.STRING)
    private StatusModel status;

    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyModel company;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    private InvestorModel investor;
}
