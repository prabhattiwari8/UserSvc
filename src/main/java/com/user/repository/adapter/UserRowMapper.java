package com.user.repository.adapter;


import com.user.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUuid(rs.getString("uuid"));
        user.setFirstName(rs.getString("first_name"));
        user.setMiddleName(rs.getString("middle_name"));
        user.setLastName(rs.getString("last_name"));
        user.setAge(rs.getInt("age"));
        user.setGender(User.Gender.valueOf(rs.getString("gender")));
        user.setPhone(rs.getString("phone"));
        user.setZip(rs.getInt("zip"));
        return user;
    }
}
