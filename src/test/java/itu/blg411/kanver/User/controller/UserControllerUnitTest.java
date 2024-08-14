// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.User.controller.UserControllerTest

package itu.blg411.kanver.User.controller;

import java.time.LocalDate;
import itu.blg411.kanver.user.UserService;
import itu.blg411.kanver.user.controller.UserController;
import itu.blg411.kanver.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    User setUpUser(Long id, String name, String email, String password, String phone, String gender, String bloodType, String tc, Integer age, LocalDate lastDonationDate, Integer donationCount) {
        User newUser = new User();
        newUser.setId(id);
        newUser.setFullName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);
        newUser.setGender(gender);
        newUser.setBloodType(bloodType);
        newUser.setTcNo(tc);
        newUser.setAge(age);
        newUser.setLastDonationDate(lastDonationDate);
        newUser.setDonationCount(donationCount);
        return newUser;
    }

    @Test
    void testCreateUser() {
        User newUser = setUpUser(1L, "John Doe", "john.doe@example.com",
                "password", "05555555555", "M", "A+",
                "12345678901", 20, LocalDate.of(2001, 1, 1), 3);

        User createdUser = setUpUser(2L, "Jane Doe", "jane.doe@example.com",
                "password", "05555555556", "F", "B+",
                "23456789012", 20, LocalDate.of(2001, 1, 1), 3);

        when(userService.createUser(newUser)).thenReturn(createdUser);

        ResponseEntity<User> response = userController.createUser(newUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdUser, response.getBody());

        verify(userService, times(1)).createUser(newUser);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;

        User user = setUpUser(1L, "John Doe", "john.doe@example.com",
                "password", "05555555555", "M", "A+",
                "12345678901", 20, LocalDate.of(2001, 1, 1), 3);

        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        verify(userService, times(1)).getUserById(userId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;

        ResponseEntity<String> response = userController.deleteUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The user is deleted successfully", response.getBody());

        verify(userService, times(1)).deleteUserById(userId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User updatedUser = setUpUser(1L, "John Doe", "john.doe@example.com",
                "password", "05555555555", "M", "A+",
                "12345678901", 20, LocalDate.of(2001, 1, 1), 3);

        User updated = setUpUser(2L, "Jane Doe", "jane.doe@example.com",
                "password", "05555555556", "F", "B+",
                "23456789012", 20, LocalDate.of(2001, 1, 1), 3);

        when(userService.updateUser(userId, updatedUser)).thenReturn(updated);

        ResponseEntity<User> response = userController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());

        verify(userService, times(1)).updateUser(userId, updatedUser);
        verifyNoMoreInteractions(userService);
    }
}
