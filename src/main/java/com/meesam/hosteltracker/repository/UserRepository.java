package com.meesam.hosteltracker.repository;

import com.meesam.hosteltracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByPhone(String phone);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userAdress WHERE u.id = :id")
    Optional<User> findByIdWithUserAddress(@Param("id") UUID id);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userAddresses")
    List<User> findAllWithUserAddresses();
}
