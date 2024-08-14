// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.User.service.UserServiceTest

package itu.blg411.kanver.User.service;

import itu.blg411.kanver.user.UserService;
import itu.blg411.kanver.user.model.User;
import itu.blg411.kanver.user.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setTcNo("12345678901");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByTcNo("12345678901")).thenReturn(false);
        when(userRepository.save(newUser)).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals(newUser.getEmail(), createdUser.getEmail());
        assertEquals(newUser.getTcNo(), createdUser.getTcNo());
    }

    @Test
    void testCreateUserEmailExists() {
        User newUser = new User();
        newUser.setEmail("existing@example.com");
        newUser.setTcNo("12345678901");

        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userService.createUser(newUser));
    }

    @Test
    void testCreateUserTcNoExists() {
        User newUser = new User();
        newUser.setEmail("new@example.com");
        newUser.setTcNo("existingTcNo");

        when(userRepository.existsByTcNo("existingTcNo")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userService.createUser(newUser));
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getUserById(userId);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testGetUserByIdNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testGetAllUsers() {
        List<User> expectedUsers = Collections.singletonList(new User());

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserByIdNotFound() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> userService.deleteUserById(userId));
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setFullName("Updated Name");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFullName("Original Name");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User resultUser = userService.updateUser(userId, updatedUser);

        assertNotNull(resultUser);
        assertEquals(updatedUser.getFullName(), resultUser.getFullName());
        assertEquals(existingUser.getEmail(), resultUser.getEmail());
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        User updatedUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.updateUser(userId, updatedUser));
    }

    @Test
    void updateUserAttributes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User updatedUser = new User();
        User existingUser = new User();

        LocalDate lastLocationDate = LocalDate.of(2024, 1, 1);

        updatedUser.setFullName("John Doe");
        updatedUser.setEmail("johndoe@example.com");
        updatedUser.setPhone("05555555555");
        updatedUser.setGender("M");
        updatedUser.setBloodType("A+");
        updatedUser.setTcNo("01234567890");
        updatedUser.setAge(1);
        updatedUser.setLastDonationDate(lastLocationDate);

        Method updateUserAttributes = UserService.class.getDeclaredMethod("updateUserAttributes", User.class, User.class);
        updateUserAttributes.setAccessible(true);

        updateUserAttributes.invoke(userService, updatedUser, existingUser);

        assertEquals("John Doe", existingUser.getFullName());
        assertEquals("johndoe@example.com", existingUser.getEmail());
        assertEquals("05555555555", existingUser.getPhone());
        assertEquals("M", existingUser.getGender());
        assertEquals("A+", existingUser.getBloodType());
        assertEquals("01234567890", existingUser.getTcNo());
        assertEquals(1, existingUser.getAge());
        assertEquals(lastLocationDate, existingUser.getLastDonationDate());
    }
}
