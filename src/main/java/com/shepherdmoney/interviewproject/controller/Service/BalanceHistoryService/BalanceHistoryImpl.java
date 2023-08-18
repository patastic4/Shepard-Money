package com.shepherdmoney.interviewproject.controller.Service.BalanceHistoryService;
import com.shepherdmoney.interviewproject.model.BalanceHistory;
import com.shepherdmoney.interviewproject.repository.BalanceHistoryRepository;
import com.shepherdmoney.interviewproject.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Patrick Chan
// Function implementation
@Service
public class BalanceHistoryImpl implements BalanceHistoryService {

    @Autowired
    BalanceHistoryRepository balanceRepository;


    // Adds provided balance history to the DB
    @Override
    public Boolean addBalanceHistory(BalanceHistory balance) throws Exception {
        if (balance.getId() != null) {
            if (balanceRepository.existsById(balance.getId())) {
                System.out.println("Balance could not be created. Card already exists.");
                throw new Exception();
            }
        }

        balanceRepository.save(balance);
        return true;
    }
}
