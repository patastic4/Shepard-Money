package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.controller.Service.UserService.UserService;
import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.vo.request.CreateUserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Patrick Chan
// APIs that access the database
// controls the functions of the user repo
@RestController
public class UserController {

    // TODO: wire in the user repository (~ 1 line)
    @Autowired
    UserService userService;

    // create User from payload and return userID if successful
    @PutMapping("/user")
    public ResponseEntity<Integer> createUser(@RequestBody CreateUserPayload payload) {
        // TODO: Create an user entity with information given in the payload, store it in the database
        //       and return the id of the user in 200 OK response
        try {
            User newUser = userService.createUser(new User(payload.getName(),payload.getEmail()));
            return new ResponseEntity<Integer>(newUser.getId(),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Integer>(400,HttpStatus.BAD_REQUEST);
        }
    }

    // deletes user if given userID exists
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam int userId) {
        // TODO: Return 200 OK if a user with the given ID exists, and the deletion is successful
        //       Return 400 Bad Request if a user with the ID does not exist
        //       The response body could be anything you consider appropriate

        try {
            boolean deleted = userService.deleteUser(userId);
            if (deleted)
            {
                return new ResponseEntity<String>("User with id " + userId +
                                                    " has been deleted",HttpStatus.OK);
            }
            return new ResponseEntity<String>("User with id " + userId +
                    " not found",HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("User with id " + userId +
                    " not found",HttpStatus.BAD_REQUEST);
        }
    }
}
