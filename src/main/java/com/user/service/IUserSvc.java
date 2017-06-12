package com.user.service;

import com.user.dto.User;
import com.user.exception.UserExistsException;
import com.user.exception.UserNotFoundException;
import com.user.exception.UserSvcException;
import java.util.List;

public interface IUserSvc {
    List<User> getUsers() throws UserSvcException;

    User createUser(User user) throws UserSvcException, UserExistsException;

    User updateUser(User user) throws UserNotFoundException, UserSvcException;
}
