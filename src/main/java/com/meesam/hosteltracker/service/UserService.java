package com.meesam.hosteltracker.service;

import com.meesam.hosteltracker.dto.UserAddressResponse;
import com.meesam.hosteltracker.dto.UserRequest;
import com.meesam.hosteltracker.dto.UserResponse;
import com.meesam.hosteltracker.model.User;
import com.meesam.hosteltracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPhone(userRequest.phone());
        user.setDob(userRequest.dob());
        user.setProfilePicturePath(userRequest.profilePicturePath());
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
                savedUser.getProfilePicturePath(),
                //savedUser.getCreatedBy(),
                //savedUser.getLastModifiedBy(),
                savedUser.getLastLoginAt(),
                null
        );
    }

    public Optional<User> findUserByPhoneNumber(String phone){
        return userRepository.findByPhone(phone);
    }

   // public User getUserAndAddressByUserId(UUID id){
       // return userRepository.findByIdWithUserAddress(id)
               // .orElseThrow(()->new RuntimeException("User not found"));
  //  }

    public List<UserResponse> getAllUsersWithAddresses() {
        return userRepository.findAllWithUserAddresses().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse mapToUserResponse(User user) {
        List<UserAddressResponse> addressResponses = user.getUserAddresses().stream()
                .map(address -> UserAddressResponse.builder()
                        .id(address.getId())
                        .address(address.getAddress())
                        .city(address.getCity())
                        .state(address.getState())
                        .country(address.getCountry())
                        .pinCode(address.getPinCode())
                        .build())
                .collect(Collectors.toList());

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getDob(),
                user.getProfilePicturePath(),
                user.getLastLoginAt(),
                addressResponses
        );
    }


}
