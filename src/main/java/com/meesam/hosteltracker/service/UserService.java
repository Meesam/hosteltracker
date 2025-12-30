package com.meesam.hosteltracker.service;

import com.meesam.hosteltracker.dto.UserRequest;
import com.meesam.hosteltracker.dto.UserResponse;
import com.meesam.hosteltracker.model.User;
import com.meesam.hosteltracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPhone(userRequest.phone());
        user.setDob(userRequest.dob());
        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getPhone(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getIsActive(),
                savedUser.getIsActivatedByOtp(),
                savedUser.getUpdatedAt(),
                savedUser.getDob(),
                savedUser.getCreatedBy(),
                savedUser.getLastModifiedBy()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
