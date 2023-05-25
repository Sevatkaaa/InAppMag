package com.sov.data;

import com.sov.model.InvestmentModel;
import lombok.Data;

@Data
public class InvestmentData {
    private Long id;
    private Long projectId;
    private Long investorId;
    private Long money;

    public static InvestmentData from(InvestmentModel investmentModel) {
        InvestmentData investmentData = new InvestmentData();
        investmentData.setId(investmentModel.getId());
        investmentData.setProjectId(investmentModel.getProjectId());
        investmentData.setInvestorId(investmentModel.getInvestorId());
        investmentData.setMoney(investmentModel.getMoney());
        return investmentData;
    }
}
