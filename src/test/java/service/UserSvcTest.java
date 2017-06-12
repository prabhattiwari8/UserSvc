package service;


import com.flextrade.jfixture.JFixture;
import com.user.dto.User;
import com.user.exception.UserExistsException;
import com.user.exception.UserNotFoundException;
import com.user.exception.UserSvcException;
import com.user.repository.UserRepo;
import com.user.service.UserSvc;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class UserSvcTest {

    @InjectMocks
    private UserSvc userSvc;

    private UserRepo userRepo;

    private JFixture jFixture;

    @Before
    public void setUp(){
        userRepo = mock(UserRepo.class);

        userSvc = new UserSvc();

        jFixture = new JFixture();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLists_success() throws UserSvcException {
        userSvc.getUsers();
        verify(userRepo, times(1)).getUsers();
    }

    @Test(expected = UserSvcException.class)
    public void getLists_throwUserSvcException() throws UserSvcException {
        when(userRepo.getUsers()).thenThrow(UserSvcException.class);
        userSvc.getUsers();
        verify(userRepo, times(1)).getUsers();
    }

    @Test
    public void createUser_success() throws UserSvcException, UserExistsException {
        User user = jFixture.create(User.class);
        when(userRepo.createUser(user)).thenReturn(user);
        when(userRepo.getUser(user.getUuid())).thenReturn(null);
        User receivedUser = userSvc.createUser(user);
        verify(userRepo, times(1)).getUser(user.getUuid());
        verify(userRepo, times(1)).createUser(user);
        Assertions.assertThat(receivedUser).isEqualToComparingFieldByField(user);
    }

    @Test(expected = UserExistsException.class)
    public void createUser_throwUserExistsException() throws UserSvcException, UserExistsException {
        User user = jFixture.create(User.class);
        when(userRepo.getUser(user.getUuid())).thenReturn(user);
        User receivedUser = userSvc.createUser(user);
        verify(userRepo, times(1)).getUser(user.getUuid());
        verifyNoMoreInteractions(userRepo);
    }

    @Test(expected = UserSvcException.class)
    public void createUser_throwUserSvcException() throws UserSvcException, UserExistsException {
        User user = jFixture.create(User.class);
        when(userRepo.getUser(user.getUuid())).thenReturn(null);
        when(userRepo.createUser(user)).thenThrow(UserSvcException.class);
        User receivedUser = userSvc.createUser(user);
        verify(userRepo, times(1)).getUser(user.getUuid());
        verify(userRepo, times(1)).createUser(user);
    }

    @Test
    public void updateUser_success() throws UserSvcException, UserNotFoundException {
        User existingUser = jFixture.create(User.class);
        User updatedUser = jFixture.create(User.class);
        when(userRepo.getUser(updatedUser.getUuid())).thenReturn(existingUser);
        when(userRepo.updateUser(any())).thenReturn(any());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User receivedUser = userSvc.updateUser(updatedUser);
        verify(userRepo, times(1)).getUser(updatedUser.getUuid());
        verify(userRepo, times(1)).updateUser(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getUuid(), is(existingUser.getUuid()));
        assertThat(userArgumentCaptor.getValue().getFirstName(), is(updatedUser.getFirstName()));
        assertThat(userArgumentCaptor.getValue().getLastName(), is(updatedUser.getLastName()));
        assertThat(userArgumentCaptor.getValue().getMiddleName(), is(updatedUser.getMiddleName()));
        assertThat(userArgumentCaptor.getValue().getAge(), is(updatedUser.getAge()));
        assertThat(userArgumentCaptor.getValue().getPhone(), is(updatedUser.getPhone()));
        assertThat(userArgumentCaptor.getValue().getGender().name(), is(updatedUser.getGender().name()));


    }

    @Test(expected = UserNotFoundException.class)
    public void updateUser_throwUserNotFoundException() throws UserSvcException, UserNotFoundException {
        User updatedUser = jFixture.create(User.class);
        when(userRepo.getUser(updatedUser.getUuid())).thenReturn(null);
        User receivedUser = userSvc.updateUser(updatedUser);
        verify(userRepo, times(1)).getUser(updatedUser.getUuid());
        verifyNoMoreInteractions(userRepo);
    }

    @Test(expected = UserSvcException.class)
    public void updateUser_throwUserSvcExceptionIfUserRepoFails() throws UserSvcException, UserNotFoundException {
        User existingUser = jFixture.create(User.class);
        User updatedUser = jFixture.create(User.class);
        when(userRepo.getUser(updatedUser.getUuid())).thenReturn(existingUser);
        when(userRepo.updateUser(any())).thenThrow(UserSvcException.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User receivedUser = userSvc.updateUser(updatedUser);
        verify(userRepo, times(1)).getUser(updatedUser.getUuid());
        verify(userRepo, times(1)).updateUser(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getUuid(), is(existingUser.getUuid()));
        assertThat(userArgumentCaptor.getValue().getFirstName(), is(updatedUser.getFirstName()));
        assertThat(userArgumentCaptor.getValue().getLastName(), is(updatedUser.getLastName()));
        assertThat(userArgumentCaptor.getValue().getMiddleName(), is(updatedUser.getMiddleName()));
        assertThat(userArgumentCaptor.getValue().getAge(), is(updatedUser.getAge()));
        assertThat(userArgumentCaptor.getValue().getPhone(), is(updatedUser.getPhone()));
        assertThat(userArgumentCaptor.getValue().getGender().name(), is(updatedUser.getGender().name()));
    }


}
