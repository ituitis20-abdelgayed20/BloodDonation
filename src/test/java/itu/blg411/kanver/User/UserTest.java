// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.User.UserTest

package itu.blg411.kanver.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import itu.blg411.kanver.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void gettersAndSetters() {
        user.setId(1L);
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password1234");
        user.setPhone("1234567890");
        user.setGender("Male");
        user.setBloodType("O+");
        user.setTcNo("12345678901");
        user.setAge(25);
        user.setLastDonationDate(LocalDate.now());
        user.setDonationCount(3);

        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getFullName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password1234", user.getPassword());
        assertEquals("1234567890", user.getPhone());
        assertEquals("Male", user.getGender());
        assertEquals("O+", user.getBloodType());
        assertEquals("12345678901", user.getTcNo());
        assertEquals(25, user.getAge());
        assertEquals(LocalDate.now(), user.getLastDonationDate());
        assertEquals(3, user.getDonationCount());
    }

    @Test
    void testJsonIgnoreAnnotation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = objectMapper.writeValueAsString(user);

        assertFalse(json.contains("bloodRequests"));
    }
}
