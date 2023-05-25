package com.sov.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "investor")
@Data
public class InvestorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private UserModel user;

    private int money = 0;

    private String description;

    @OneToMany(mappedBy = "investor")
    private List<ProjectModel> investments;
}
