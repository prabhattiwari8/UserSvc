package com.user.controller;

import com.user.dto.User;
import com.user.dto.User.CreateUserGroup;
import com.user.dto.User.UpdateUserGroup;
import com.user.exception.UserExistsException;
import com.user.exception.UserNotFoundException;
import com.user.exception.UserSvcException;
import com.user.service.IUserSvc;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserSvc userSvc;

    @GetMapping("/")
    public List<User> getUsers() throws UserSvcException {
        return userSvc.getUsers();
    }

    @PutMapping
    public User updateUser(@Validated(UpdateUserGroup.class) @RequestBody User user) throws UserNotFoundException, UserSvcException {
        return userSvc.updateUser(user);
    }

    @PostMapping
    public User createUser(@Validated(CreateUserGroup.class) @RequestBody User user) throws  UserExistsException, UserSvcException {
        return userSvc.createUser(user);
    }
}
