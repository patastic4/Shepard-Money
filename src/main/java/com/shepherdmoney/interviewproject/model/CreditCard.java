package com.shepherdmoney.interviewproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String issuanceBank;

    private String number;

    // mapping to user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    // tracks balance history entries
    @OneToMany(mappedBy="creditCardId",cascade = CascadeType.ALL)
    private Set<BalanceHistory> balanceHistories = new TreeSet<BalanceHistory>();


    public CreditCard(String issuanceBank, String number, User userId) {
        this.issuanceBank = issuanceBank;
        this.number = number;
        this.user = userId;
    }



    // TODO: Credit card's owner. For detailed hint, please see User class

    // TODO: Credit card's balance history. It is a requirement that the dates in the balanceHistory 
    //       list must be in chronological order, with the most recent date appearing first in the list. 
    //       Additionally, the first object in the list must have a date value that matches today's date, 
    //       since it represents the current balance of the credit card. For example:
    //       [
    //         {date: '2023-04-13', balance: 1500},
    //         {date: '2023-04-12', balance: 1200},
    //         {date: '2023-04-11', balance: 1000},
    //         {date: '2023-04-10', balance: 800}
    //       ]
}
