package com.shepherdmoney.interviewproject.controller.Service.CreditCardService;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Patrick Chan
// function implementations

@Service
public class CreditCardServiceImpl implements CreditCardService{
    @Autowired
    CreditCardRepository cardRepository;

    // Return list of all credit cards in the Database
    @Override
    public List<CreditCard> getAll() {
        return cardRepository.findAll();
    }


    // Return list of all credit cards for given userID
    @Override
    public List<CreditCard> getCreditCardByID(Integer userID) throws Exception {
        if (cardRepository.findByUserId(userID).isEmpty()) {
            System.out.println("Card with id:" + userID + " could not be found.");
            throw new Exception();
        } else {
            return cardRepository.findByUserId(userID);
        }
    }

    // Return credit card given the card number
    @Override
    public CreditCard getCreditCardByNumber(String cardNumber) throws Exception {
        if (cardRepository.findByNumber(cardNumber) == null) {
            System.out.println("Card with id:" + cardNumber + " could not be found.");
            throw new Exception();
        } else {
            return cardRepository.findByNumber(cardNumber);
        }
    }


    // Save given credit card to repo and return the card with ID assigned
    @Override
    public CreditCard createCreditCard(CreditCard creditCard) throws Exception {
        if (creditCard.getId() != null) {
            if (cardRepository.existsById(creditCard.getId())) {
                System.out.println("Card could not be created. Card already exists.");
                throw new Exception();
            }
        }
        return cardRepository.save(creditCard);
    }

    // Given creditCardID and updated Credit Card update the card and return the new card
    // Note: can change this function to find card by card number instead of ID, however
    //       since checking dor correct card numbers were not needed we can have duplicate card
    //       numbers so for simplicity I choose to find by ID
    @Override
    public CreditCard updateCreditCard(Integer creditCardID, CreditCard creditCard) throws Exception {
        if (!cardRepository.existsById(creditCard.getId())) {
            System.out.println("Card could not be updated. Card does not exists.");
            throw new Exception();
        } else {
            return cardRepository.save(creditCard);
        }
    }

    // Unused but given card ID delete the card
    @Override
    public boolean deleteCreditCardByID(Integer creditCardID) throws Exception {
        if (!cardRepository.existsById(creditCardID)) {
            System.out.println("Card could not be deleted. Card does not exists.");
            throw new Exception();
        } else {
            cardRepository.deleteById(creditCardID);
            return true;
        }
    }
}
