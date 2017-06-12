package controller;


import com.flextrade.jfixture.JFixture;
import com.user.dto.User;
import com.user.controller.UserController;
import com.user.exception.UserExistsException;
import com.user.exception.UserNotFoundException;
import com.user.exception.UserSvcException;
import com.user.service.IUserSvc;
import com.user.service.UserSvc;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    private IUserSvc userSvc;

    private JFixture jFixture;

    @Before
    public void setUp(){
        userSvc = mock(UserSvc.class);

        jFixture=new JFixture();

        userController = new UserController();

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getLists_success() throws UserSvcException {
        userController.getUsers();
        verify(userSvc, times(1)).getUsers();
    }

    @Test
    public void createUser_success() throws UserSvcException, UserExistsException {
        User user = jFixture.create(User.class);
        userController.createUser(user);
        verify(userSvc, times(1)).createUser(user);
    }

    @Test
    public void UpdateUser_success() throws UserSvcException, UserNotFoundException {
        User user = jFixture.create(User.class);
        userController.updateUser(user);
        verify(userSvc, times(1)).updateUser(user);
    }
}
