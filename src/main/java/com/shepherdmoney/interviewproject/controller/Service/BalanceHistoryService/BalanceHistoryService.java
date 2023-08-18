package com.shepherdmoney.interviewproject.controller.Service.BalanceHistoryService;

import com.shepherdmoney.interviewproject.model.BalanceHistory;

// Patrick Chan
// Balance History Interface
// Functions that interact with BalanceHistoryRepo

public interface BalanceHistoryService {

    Boolean addBalanceHistory(BalanceHistory balance) throws Exception;

}
