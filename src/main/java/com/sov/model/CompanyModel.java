package com.sov.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
@Data
public class CompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private UserModel user;

    @OneToMany(mappedBy = "company")
    private List<ProjectModel> projects;

}
