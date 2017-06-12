package com.user.service;


import com.user.dto.User;
import com.user.exception.UserExistsException;
import com.user.exception.UserNotFoundException;
import com.user.exception.UserSvcException;
import com.user.repository.IUserRepo;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class UserSvc implements IUserSvc {
    @Autowired
    IUserRepo userRepo;

    @Override
    public List<User> getUsers() throws UserSvcException {
        return userRepo.getUsers();
    }

    private User getUser(String id) throws UserSvcException {
        return userRepo.getUser(id);
    }

    @Override
    public User createUser(User user) throws UserSvcException, UserExistsException {
        if(!StringUtils.isEmpty(user.getUuid())) {
            User storedUser = getUser(user.getUuid());
            if (!ObjectUtils.isEmpty(storedUser)) {
                throw new UserExistsException("User already exists with uuid "+user.getUuid());
            }
        }else{
            user.setUuid(UUID.randomUUID().toString());
        }
        return userRepo.createUser(user);
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException, UserSvcException {
        User storedUser = getUser(user.getUuid());
        if(ObjectUtils.isEmpty(storedUser)){
            throw new UserNotFoundException("user not found");
        }

        if(!StringUtils.isEmpty(user.getFirstName()))storedUser.setFirstName(user.getFirstName());
        if(!StringUtils.isEmpty(user.getMiddleName()))storedUser.setMiddleName(user.getMiddleName());
        if(!StringUtils.isEmpty(user.getLastName()))storedUser.setLastName(user.getLastName());
        if(!StringUtils.isEmpty(user.getAge()))storedUser.setAge(user.getAge());
        if(!StringUtils.isEmpty(user.getGender()))storedUser.setGender(user.getGender());
        if(!StringUtils.isEmpty(user.getPhone()))storedUser.setPhone(user.getPhone());
        if(!StringUtils.isEmpty(user.getZip()))storedUser.setZip(user.getZip());

        return userRepo.updateUser(storedUser);
    }
}
