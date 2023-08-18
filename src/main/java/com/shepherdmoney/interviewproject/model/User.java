package com.shepherdmoney.interviewproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "MyUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // tracks credit cards
    @OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    private Set<CreditCard> creditCardId = new HashSet<CreditCard>();

    private String name;

    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
//private List<CreditCard> userCards;


    // TODO: User's credit card
    // HINT: A user can have one or more, or none at all. We want to be able to query credit cards by user
    //       and user by a credit card.
}
