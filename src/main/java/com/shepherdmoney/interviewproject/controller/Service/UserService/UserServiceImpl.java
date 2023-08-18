package com.shepherdmoney.interviewproject.controller.Service.UserService;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.repository.CreditCardRepository;
import com.shepherdmoney.interviewproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

// Patrick Chan
// function implementations
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    // given a User, save that user to the database and return user with ID assigned
    @Override
    public User createUser(User user) throws Exception {

        if (user.getId() != null) {
            if (userRepository.existsById(user.getId())) {
                System.out.println("Card could not be created. Card already exists.");
                throw new Exception();
            }
        }
        return userRepository.save(user);
    }

    // given userid delete the User from database
    // return true if deleted
    @Override
    public boolean deleteUser(Integer userId) throws Exception {
        if (!userRepository.existsById(userId)) {
            System.out.println("Card could not be deleted. Card does not exists.");
            throw new Exception();

        } else {
            userRepository.deleteById(userId);
            return true;
        }
    }

    // given userid get the user and return it
    @Override
    public User getUserById(Integer userId) throws Exception
    {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            System.out.println("Card with id:" + userId + " could not be found.");
            throw new Exception();
        } else {
            return user.get();
        }
    }

    // given a Credit Card save to user
    @Override
    public void addCreditCard(User user, CreditCard card) throws Exception
    {

        Set<CreditCard> cards = user.getCreditCardId();
        cards.add(card);

        userRepository.save(user);
    }
}
