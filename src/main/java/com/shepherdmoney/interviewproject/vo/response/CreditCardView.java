package com.shepherdmoney.interviewproject.vo.response;

import com.shepherdmoney.interviewproject.model.BalanceHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class CreditCardView {

    private String issuanceBank;

    private String number;

    private Set<BalanceHistory> balanceHistories;
}
