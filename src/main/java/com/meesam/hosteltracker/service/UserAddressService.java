package com.meesam.hosteltracker.service;

import com.meesam.hosteltracker.dto.UserAddressRequest;
import com.meesam.hosteltracker.dto.UserResponse;
import com.meesam.hosteltracker.model.User;
import com.meesam.hosteltracker.model.UserAddress;
import com.meesam.hosteltracker.repository.UserAddressRepository;
import com.meesam.hosteltracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public UserResponse addUserAddress(UserAddressRequest userAddressRequest) {
        User user = userRepository.findById(userAddressRequest.userId()).orElseThrow(() -> new RuntimeException("User not found"));

        UserAddress userAddress = UserAddress.builder()
                .address(userAddressRequest.address())
                .city(userAddressRequest.city())
                .state(userAddressRequest.state())
                .country(userAddressRequest.country())
                .pinCode(userAddressRequest.pinCode())
                .users(user)
                .build();

        userAddressRepository.save(userAddress);
        return userService.mapToUserResponse(user);
    }
}
