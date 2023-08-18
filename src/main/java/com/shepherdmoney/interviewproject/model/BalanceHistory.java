package com.shepherdmoney.interviewproject.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BalanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private double balance;

    private Instant date;


    // mapping to credit card
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="card_Id", nullable=false)
    @JsonIgnore
    private CreditCard creditCardId;

    public BalanceHistory(double balance, Instant date, CreditCard creditCardId) {
        this.balance = balance;
        this.date = date;
        this.creditCardId = creditCardId;
    }
}
