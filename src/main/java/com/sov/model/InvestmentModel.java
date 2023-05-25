package com.sov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "investment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "investor_id")
    private Long investorId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "money")
    private Long money;

    public InvestmentModel(Long investorId, Long projectId, Long money) {
        this.investorId = investorId;
        this.projectId = projectId;
        this.money = money;
    }
}
