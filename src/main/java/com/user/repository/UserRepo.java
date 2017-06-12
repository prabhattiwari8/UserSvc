package com.user.repository;

import com.user.dto.User;
import com.user.exception.UserSvcException;
import com.user.repository.adapter.UserRowMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo implements IUserRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRowMapper userRowMapper;

    @Override
    public List<User> getUsers() throws UserSvcException {
        try {
            return jdbcTemplate.query("SELECT *  FROM USER", userRowMapper);
        }catch (Exception ex){
            throw new UserSvcException("Error while fetching list of users "+ex.getMessage(), ex);
        }
    }

    @Override
    public User getUser(String id) throws UserSvcException{
        try {
            return jdbcTemplate.queryForObject("SELECT *  FROM USER WHERE uuid=?", new Object[]{id}, userRowMapper);
        }catch(EmptyResultDataAccessException ex){
            return null;
        }catch(Exception ex){
            throw new UserSvcException("Exception" +ex.getMessage()+" while fetching user with uuid  "+id, ex);
        }
    }

    @Override
    public User updateUser(User user) throws UserSvcException {
        String SQL = "UPDATE USER SET first_name=?, middle_name=?, last_name=? , age=?, gender=?, phone=?, zip=? WHERE uuid=? ";
        try {
            jdbcTemplate.update(SQL,
                    user.getFirstName(),
                    user.getMiddleName(),
                    user.getLastName(),
                    user.getAge(),
                    user.getGender().name(),
                    user.getPhone(),
                    user.getZip(),
                    user.getUuid());
            return user;
        }catch (Exception ex){
                throw new UserSvcException("Error while updating user "+ex.getMessage(), ex);
        }
    }

    @Override
    public User createUser(User user) throws UserSvcException {


        String SQL = "INSERT INTO USER (uuid, first_name, middle_name, last_name, age, gender, phone, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            jdbcTemplate.update(SQL, user.getUuid(),
                    user.getFirstName(),
                    user.getMiddleName(),
                    user.getLastName(),
                    user.getAge(),
                    user.getGender().name(),
                    user.getPhone(),
                    user.getZip());

        }catch (Exception ex){
            throw new UserSvcException("Error while creating user "+ex.getMessage(), ex);
        }
        return user;
    }
}
