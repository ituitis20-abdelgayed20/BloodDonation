package itu.blg411.kanver.User.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import itu.blg411.kanver.user.model.User;
import itu.blg411.kanver.user.model.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        User newUser = createTestUser("John Doe", "john.doe@example.com", "34567890123");

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.fullName").value(newUser.getFullName()))
                .andExpect(jsonPath("$.email").value(newUser.getEmail()))
                .andExpect(jsonPath("$.tcNo").value(newUser.getTcNo()))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        User createdUser = objectMapper.readValue(content, User.class);

        assertEquals(newUser.getFullName(), createdUser.getFullName());
        assertEquals(newUser.getEmail(), createdUser.getEmail());
        assertEquals(newUser.getTcNo(), createdUser.getTcNo());
    }

    @Test
    public void testGetUserById() throws Exception {
        User newUser = createTestUser("Jane Doe", "jane.doe@example.com", "98765432101");
        User createdUser = userRepository.save(newUser);

        mockMvc.perform(get("/users/{userId}", createdUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUser.getId()))
                .andExpect(jsonPath("$.fullName").value(createdUser.getFullName()))
                .andExpect(jsonPath("$.email").value(createdUser.getEmail()))
                .andExpect(jsonPath("$.tcNo").value(createdUser.getTcNo()));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = createTestUser("User 1", "user1@example.com", "11111111111");
        User user2 = createTestUser("User 2", "user2@example.com", "22222222222");

        userRepository.saveAll(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User newUser = createTestUser("Original Name", "original.email@example.com", "23456789012");
        User createdUser = userRepository.save(newUser);

        User updatedUser = new User();
        updatedUser.setFullName("Updated Name");
        updatedUser.setEmail("updated.email@example.com");

        mockMvc.perform(put("/users/{userId}", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(updatedUser.getFullName()))
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        User newUser = createTestUser("ToDelete", "todelete@example.com", "12345678901");
        User createdUser = userRepository.save(newUser);

        mockMvc.perform(delete("/users/{userId}", createdUser.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/{userId}", createdUser.getId()))
                .andExpect(status().isNotFound());
    }

    private User createTestUser(String fullName, String email, String tcNo) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setTcNo(tcNo);
        return user;
    }
}