package com.shepherdmoney.interviewproject.controller.Service.UserService;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.User;


// Patrick Chan
// User Interface
// Functions that interact with UserRepo

public interface UserService {

    User createUser(User user) throws Exception;
    boolean deleteUser(Integer userid)throws Exception;
    User getUserById(Integer userId) throws Exception;
    void addCreditCard(User user, CreditCard card) throws Exception;
}
