package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.controller.Service.BalanceHistoryService.BalanceHistoryService;
import com.shepherdmoney.interviewproject.controller.Service.CreditCardService.CreditCardService;
import com.shepherdmoney.interviewproject.controller.Service.UserService.UserService;
import com.shepherdmoney.interviewproject.model.BalanceHistory;
import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.vo.request.AddCreditCardToUserPayload;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import com.shepherdmoney.interviewproject.vo.response.CreditCardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Patrick Chan
// APIs that access the database
// controls the functions of the creditcard repo

@RestController
public class CreditCardController {

    // TODO: wire in CreditCard repository here (~1 line)

    @Autowired
    CreditCardService cardService;

    @Autowired
    UserService userService;

    @Autowired
    BalanceHistoryService balanceService;

    // adds a given credit card to the user
    @PostMapping("/credit-card")
    public ResponseEntity<Integer> addCreditCardToUser(@RequestBody AddCreditCardToUserPayload payload) {
        // TODO: Create a credit card entity, and then associate that credit card with user with given userId
        //       Return 200 OK with the credit card id if the user exists and credit card is successfully associated with the user
        //       Return other appropriate response code for other exception cases
        //       Do not worry about validating the card number, assume card number could be any arbitrary format and length
        try {
            User user = userService.getUserById(payload.getUserId());
            CreditCard newCard = new CreditCard(payload.getCardIssuanceBank(),payload.getCardNumber(),user);
            newCard = cardService.createCreditCard(newCard);
            userService.addCreditCard(user, newCard);
            return new ResponseEntity<Integer>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
    }

    // get and return all cards by userID
    @GetMapping("/credit-card:all")
    public ResponseEntity<List<CreditCardView>> getAllCardOfUser(@RequestParam int userId) {
        // TODO: return a list of all credit card associated with the given userId, using CreditCardView class
        //       if the user has no credit card, return empty list, never return null

        try {
            // My attempt to sort and display balance histories
            // Note: I tried to use a comparator in the CreditCard Model however it did not work so for now
            // I retrieve all the balances and sort them here
            Comparator<BalanceHistory> instantComparator = new Comparator<BalanceHistory>() {
                public int compare(BalanceHistory emp1, BalanceHistory emp2) {
                    return emp2.getDate().compareTo(emp1.getDate());
                }
            };

            // Stream to collect cards and map them to list with sorted balance history
            List<CreditCardView> creditCardView = cardService.getCreditCardByID(userId).stream()
                    .map(card -> new CreditCardView(card.getIssuanceBank(), card.getNumber(),
                            card.getBalanceHistories().stream().collect(Collectors.toCollection(() -> new TreeSet<>(instantComparator)))))
                    .collect(Collectors.toList());
            // return the list for the caller of API
            return new ResponseEntity<List<CreditCardView>>(creditCardView,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<CreditCardView>>(Collections.emptyList() ,HttpStatus.BAD_REQUEST);
        }
    }

    // get user associated with credit card number
    @GetMapping("/credit-card:user-id")
    public ResponseEntity<Integer> getUserIdForCreditCard(@RequestParam String creditCardNumber) {
        // TODO: Given a credit card number, efficiently find whether there is a user associated with the credit card
        //       If so, return the user id in a 200 OK response. If no such user exists, return 400 Bad Request
        try {
            CreditCard userCard = cardService.getCreditCardByNumber(creditCardNumber);
            if(userCard == null)
            {
                return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Integer>(userCard.getUser().getId(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
    }

    // given array of balance histories add them to the credit card and return balance if successful
    @PostMapping("/credit-card:update-balance")
    public ResponseEntity<List<BalanceHistory>> postMethodName(@RequestBody UpdateBalancePayload[] payload) {
        //TODO: Given a list of transactions, update credit cards' balance history.
        //      For example: if today is 4/12, a credit card's balanceHistory is [{date: 4/12, balance: 110}, {date: 4/10, balance: 100}],
        //      Given a transaction of {date: 4/10, amount: 10}, the new balanceHistory is
        //      [{date: 4/12, balance: 120}, {date: 4/11, balance: 110}, {date: 4/10, balance: 110}]
        //      Return 200 OK if update is done and successful, 400 Bad Request if the given card number
        //        is not associated with a card.

        // save new payload to return to caller of API
        List<BalanceHistory> finishedPayload = new ArrayList<>();
        // Loops through array payload
        for (UpdateBalancePayload item : payload)
        {
            try {

                CreditCard card = cardService.getCreditCardByNumber(item.getCreditCardNumber());
                // create and add new balance to balance repo
                BalanceHistory balance = new BalanceHistory( item.getTransactionAmount(), item.getTransactionTime(), card);
                balanceService.addBalanceHistory(balance);
                // now link that balance to credit card

                card.getBalanceHistories().add(balance);

                cardService.updateCreditCard(card.getId(), card);
                finishedPayload.add(balance);
            } catch (Exception e) {
                return new ResponseEntity<List<BalanceHistory>>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
            }
        }

        return new  ResponseEntity<List<BalanceHistory>>(finishedPayload, HttpStatus.OK);


    }

    
}
