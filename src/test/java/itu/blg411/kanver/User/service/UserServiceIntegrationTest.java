package itu.blg411.kanver.User.service;

import itu.blg411.kanver.user.UserService;
import itu.blg411.kanver.user.model.User;
import itu.blg411.kanver.user.model.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        User newUser = new User();
        newUser.setFullName("John Doe");
        newUser.setEmail("john.doe@example.com");
        newUser.setTcNo("12345678901");

        User createdUser = userService.createUser(newUser);

        assertEquals(newUser.getFullName(), createdUser.getFullName());
        assertEquals(newUser.getEmail(), createdUser.getEmail());
        assertEquals(newUser.getTcNo(), createdUser.getTcNo());
    }

    @Test
    public void testGetUserById() {
        User newUser = new User();
        newUser.setFullName("Jane Doe");
        newUser.setEmail("jane.doe@example.com");
        newUser.setTcNo("98765432101");

        User createdUser = userService.createUser(newUser);

        User retrievedUser = userService.getUserById(createdUser.getId());

        assertEquals(createdUser.getId(), retrievedUser.getId());
        assertEquals(createdUser.getFullName(), retrievedUser.getFullName());
        assertEquals(createdUser.getEmail(), retrievedUser.getEmail());
        assertEquals(createdUser.getTcNo(), retrievedUser.getTcNo());
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setFullName("User 1");
        user1.setEmail("user1@example.com");
        user1.setTcNo("11111111111");

        User user2 = new User();
        user2.setFullName("User 2");
        user2.setEmail("user2@example.com");
        user2.setTcNo("22222222222");

        List<User> allUsersOld = userService.getAllUsers();

        userService.createUser(user1);
        userService.createUser(user2);

        List<User> allUsersNew = userService.getAllUsers();

        Integer difference = allUsersNew.size() - allUsersOld.size();

        assertEquals(2, difference);
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User();
        newUser.setFullName("Original Name");
        newUser.setEmail("original.email@example.com");
        newUser.setTcNo("12345678901");

        User createdUser = userService.createUser(newUser);

        User updatedUser = new User();
        updatedUser.setFullName("Updated Name");
        updatedUser.setEmail("updated.email@example.com");

        User result = userService.updateUser(createdUser.getId(), updatedUser);

        assertEquals(updatedUser.getFullName(), result.getFullName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
    }

    @Test
    public void testDeleteUserById() {
        User newUser = new User();
        newUser.setFullName("ToDelete");
        newUser.setEmail("todelete@example.com");
        newUser.setTcNo("12345678901");

        User createdUser = userService.createUser(newUser);

        userService.deleteUserById(createdUser.getId());

        assertThrows(ResponseStatusException.class, () -> userService.getUserById(createdUser.getId()));
    }
}