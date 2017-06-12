package com.user.repository;

import com.user.dto.User;
import com.user.exception.UserSvcException;
import java.util.List;

public interface IUserRepo {
    List<User> getUsers() throws UserSvcException;

    User getUser(String id) throws UserSvcException;

    User updateUser(User user) throws UserSvcException;

    User createUser(User user) throws UserSvcException;
}
