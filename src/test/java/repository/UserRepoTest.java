package repository;


import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.utility.SpecimenType;
import com.user.dto.User;
import com.user.exception.UserSvcException;
import com.user.repository.UserRepo;
import com.user.repository.adapter.UserRowMapper;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRepoTest {


    private JdbcTemplate jdbcTemplate;

    private UserRowMapper userRowMapper;

    @InjectMocks
    private UserRepo userRepo = null;

    private JFixture jfixture;

    @Before
    public void setUp(){

        jdbcTemplate = Mockito.mock(JdbcTemplate.class);

        userRowMapper = Mockito.mock(UserRowMapper.class);

        userRepo = new UserRepo();

        jfixture = new JFixture();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsers_shouldReturnListOfUsers() throws UserSvcException {
        when(jdbcTemplate.query(anyString(), any(UserRowMapper.class))).thenReturn(jfixture.create(new SpecimenType<List<User>>(){}));
        List<User> userList = userRepo.getUsers();
        assertThat(userList, notNullValue());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRowMapper.class));
    }

    @Test(expected = UserSvcException.class)
    public void getUsers_shouldThrowUserSvcExceptionIfTemplateThrowsError() throws UserSvcException {
        when(jdbcTemplate.query(anyString(), any(UserRowMapper.class))).thenThrow(Exception.class);
        List<User> userList = userRepo.getUsers();
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRowMapper.class));
    }

    @Test
    public void getUser_shouldReturnUserWithID() throws UserSvcException {
        User testuser = jfixture.create(User.class);
        when(jdbcTemplate.queryForObject(anyString(),any(),any(UserRowMapper.class))).thenReturn(testuser);
        User response = userRepo.getUser(testuser.getUuid());
        assertThat(response.getUuid(), is(testuser.getUuid()));
        verify(jdbcTemplate, times(1)).queryForObject(anyString(),any(),any(UserRowMapper.class));
    }

    @Test
    public void getUser_shouldReturnNullIfTemplateThrowEmptyResultDataAccessException() throws UserSvcException {
        User testuser = jfixture.create(User.class);
        when(jdbcTemplate.queryForObject(anyString(),any(),any(UserRowMapper.class))).thenThrow(EmptyResultDataAccessException.class);
        User response = userRepo.getUser(testuser.getUuid());
        assertThat(response, nullValue());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(),any(),any(UserRowMapper.class));
    }

    @Test(expected = UserSvcException.class)
    public void getUser_shouldThrowUserSvcExceptionIfTemplateThrowAnyExceptionButEmptyResultDataAccessException() throws UserSvcException {
        User testuser = jfixture.create(User.class);
        when(jdbcTemplate.queryForObject(anyString(),any(),any(UserRowMapper.class))).thenThrow(Exception.class);
        User response = userRepo.getUser(testuser.getUuid());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(),any(),any(UserRowMapper.class));
    }

    @Test
    public void createUser_success() throws UserSvcException {
        User insertUser = jfixture.create(User.class);
        String SQL = "UPDATE USER SET first_name=?, middle_name=?, last_name=? , age=?, gender=?, phone=?, zip=? WHERE uuid=? ";
        // when(jdbcTemplate.update(anyString(),anyString(),anyString(),anyString(),anyString(),anyInt(),anyString(),anyString(),anyInt())).thenReturn(anyInt());
        when(jdbcTemplate.update(SQL,
                                 insertUser.getUuid(),
                                 insertUser.getFirstName(),
                                 insertUser.getMiddleName(),
                                 insertUser.getLastName(),
                                 insertUser.getAge(),
                                 insertUser.getGender().name(),
                                 insertUser.getPhone(),
                                 insertUser.getZip())).thenReturn(jfixture.create(Integer.class));
        User createdUser = userRepo.createUser(insertUser);
        assertThat(createdUser).isEqualToComparingFieldByField(insertUser);
    }

    @Test(expected = UserSvcException.class)
    public void createUser_shouldThrowUserSvcExceptionIfTemplateThrowAnyException() throws UserSvcException {
        User insertUser = jfixture.create(User.class);
        when(jdbcTemplate.update(anyString(),anyString(),anyString(),anyString(),anyString(),anyInt(),anyString(),anyString(),anyInt())).thenThrow(Exception.class);
        userRepo.createUser(insertUser);
    }

    @Test
    public void updateUser_success() throws UserSvcException {
        User updateUser = jfixture.create(User.class);
        String SQL = "UPDATE USER SET first_name=?, middle_name=?, last_name=? , age=?, gender=?, phone=?, zip=? WHERE uuid=? ";
        when(jdbcTemplate.update(SQL,
                updateUser.getFirstName(),
                updateUser.getMiddleName(),
                updateUser.getLastName(),
                updateUser.getAge(),
                updateUser.getGender().name(),
                updateUser.getPhone(),
                updateUser.getZip(),
                updateUser.getUuid())).thenReturn(jfixture.create(Integer.class));

        User updatedUser = userRepo.updateUser(updateUser);
        assertThat(updatedUser).isEqualToComparingFieldByField(updateUser);
    }

    @Test(expected = UserSvcException.class)
    public void updateUser_shouldThrowUserSvcExceptionIfTemplateThrowAnyException() throws UserSvcException {
        User updateUser = jfixture.create(User.class);
        when(jdbcTemplate.update(anyString(),anyString(),anyString(),anyString(),anyInt(),anyString(),anyString(),anyInt(),anyString())).thenThrow(Exception.class);
        userRepo.updateUser(updateUser);
    }
}
