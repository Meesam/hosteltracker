package com.meesam.hosteltracker.service;

import com.meesam.hosteltracker.dto.UserRequest;
import com.meesam.hosteltracker.dto.UserResponse;
import com.meesam.hosteltracker.model.User;
import com.meesam.hosteltracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPhone(userRequest.phone());
        user.setDob(userRequest.dob());
        String password = "SecureRandomPassword";
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getPhone(),
                savedUser.getEmail(),
               // savedUser.getCreatedAt(),
               // savedUser.getIsActive(),
               // savedUser.getIsActivatedByOtp(),
               // savedUser.getUpdatedAt(),
                savedUser.getDob(),
                //savedUser.getCreatedBy(),
                //savedUser.getLastModifiedBy(),
                savedUser.getLastLoginAt()
        );
    }

    public Optional<User> findUserByPhoneNumber(String phone){
        return userRepository.findByPhone(phone);
    }


}
