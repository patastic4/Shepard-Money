package com.shepherdmoney.interviewproject.controller.Service.CreditCardService;

import com.shepherdmoney.interviewproject.model.CreditCard;

import java.util.List;


// Patrick Chan
// Credit Card Interface
// Functions that interact with CreditCardRepo

public interface CreditCardService {
    List<CreditCard> getAll();
    List<CreditCard> getCreditCardByID(Integer creditCardID) throws Exception;
    CreditCard getCreditCardByNumber(String cardNumber) throws Exception;
    CreditCard createCreditCard(CreditCard creditCard) throws Exception;
    CreditCard updateCreditCard(Integer creditCardID, CreditCard creditCard) throws Exception;
    boolean deleteCreditCardByID(Integer creditCardID)throws Exception;
}
