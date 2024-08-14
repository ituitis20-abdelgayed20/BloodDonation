package itu.blg411.kanver.user;

import itu.blg411.kanver.user.model.User;
import itu.blg411.kanver.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User newUser) {
        validateUserEmailUniqueness(newUser.getEmail());
        validateUserTcNoUniqueness(newUser.getTcNo());
        newUser.setDonationCount(0);
        return userRepository.save(newUser);
    }
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long userId, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            updateUserAttributes(updatedUser, existingUser);
            return userRepository.save(existingUser);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }
    }

    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }
    }

    private void updateUserAttributes(User updatedUser, User existingUser) {
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(existingUser.getEmail())) {
            validateUserEmailUniqueness(updatedUser.getEmail());
        }
        existingUser.setFullName(updatedUser.getFullName() != null ? updatedUser.getFullName() : existingUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : existingUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone() != null ? updatedUser.getPhone() : existingUser.getPhone());
        existingUser.setGender(updatedUser.getGender() != null ? updatedUser.getGender() : existingUser.getGender());
        existingUser.setBloodType(updatedUser.getBloodType() != null ? updatedUser.getBloodType() : existingUser.getBloodType());
        existingUser.setTcNo(updatedUser.getTcNo() != null ? updatedUser.getTcNo() : existingUser.getTcNo());
        existingUser.setAge(updatedUser.getAge() != null ? updatedUser.getAge() : existingUser.getAge());
        existingUser.setLastDonationDate(updatedUser.getLastDonationDate() != null ? updatedUser.getLastDonationDate() : existingUser.getLastDonationDate());
    }


    private void validateUserEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with the same email already exists");
        }
    }

    private void validateUserTcNoUniqueness(String tcNo) {
        if (userRepository.existsByTcNo(tcNo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with the same TC number already exists");
        }
    }
}
